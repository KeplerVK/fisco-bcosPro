package org.bcos.evidence.app;

import org.bcos.evidence.web3j.Transaction;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyStore;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.util.*;
import org.fisco.bcos.channel.client.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.bcos.evidence.sample.EvidenceData;
import org.bcos.evidence.sample.PublicAddressConf;
import org.bcos.evidence.util.Tools;
import org.bcos.evidence.web3j.Evidence;
import org.bcos.evidence.web3j.EvidenceSignersData;
import org.fisco.bcos.web3j.tuples.generated.Tuple7;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.crypto.Sign;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

public class BussiApp{
	private Transaction tran;
	private Web3j web3j;
	ApplicationContext context;
	ConcurrentHashMap<String, String> addressConf;
	
	public static BigInteger gasPrice = new BigInteger("99999999999");
	public static BigInteger gasLimited = new BigInteger("9999999999999");
	
	public BussiApp() {
		tran=null;
		web3j=null;
		context=null;
		addressConf=null;
	}
	
	//loadConfig
	public boolean loadConfig() throws Exception{
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Service service = context.getBean(Service.class);
        service.run();
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        web3j = Web3j.build(channelEthereumService,service.getGroupId());
        boolean flag=false;
        if(web3j!=null){
        	flag=true;
        }
        PublicAddressConf conf = context.getBean(PublicAddressConf.class);
        addressConf = conf.getAllPublicAddress();
        return flag;
	}
	
	//deploy
	public Address deployContract(String keyStoreFileName,String keyStorePassword, String keyPassword) throws Exception{
		if (web3j == null )
			return null;
		Credentials credentials=loadkey(keyStoreFileName,keyStorePassword,keyPassword);
		if(credentials==null){
			return null;
		}
	    //Service service = context.getBean(Service.class);
	    //service.run();
		try {
			tran=Transaction.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimited)).send();
			//set bank
			tran.setBank().send();
			System.out.println("set bank successfully!");
			System.out.println("deploy sucess!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new Address(tran.getContractAddress());
	}
	
	//init three accounts
	public Boolean initCompany(String keyStoreFileName,String keyStorePassword,String keyPassword,String address,String CompN,String num,String creRate)throws Exception{
		Boolean flag = false;
		BigInteger balan=new BigInteger(num);
		BigInteger creR=new BigInteger(creRate);
		if (web3j == null ) {
			System.out.println("did not set web3j");
		}
		
		Credentials credentials=loadkey(keyStoreFileName,keyStorePassword,keyPassword);
		if(credentials==null){
			System.out.println("did not set credentials");
		}
		
		try {
			Transaction tran1=Transaction.load(address.toString(), web3j,  credentials, new StaticGasProvider(gasPrice, gasLimited));
			//set Bank
			tran1.newCompany(CompN).send();
			System.out.printf("init company '%s' successfully!\n" , CompN);
			Credentials bankCred=loadkey("user.jks","123456","123456");
			Transaction theBank=Transaction.load(address.toString(), web3j, bankCred,new StaticGasProvider(gasPrice, gasLimited));
			if(theBank!=null) {
				//get public key
				String addr=getPublicKey(keyStoreFileName,keyStorePassword,keyPassword);
				System.out.println(addr);
				System.out.println(addressConf.get("User"));
				if(addressConf.get("bjyx")==null) {
					System.out.println("nothing");
				}
				else {
					System.out.println(addressConf.get("bjyx"));
				}
				
				//theBank set Balance for the company
				theBank.setBalance(addr,balan).send();
				System.out.printf("init '%s' balance successfully!\n", CompN);
				
				//theBank set creditRating for the comapy
				theBank.setCreditRating(addr,creR).send();
				System.out.printf("init '%s' creditRating successfully!\n" , CompN);
				
				flag=true;
			}else {
				System.out.printf("init '%s' balance and creditRating failed\n", CompN);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	//buy things from another company through oweing money
	public Boolean buySthFromAnoComp(String keyStoreFileName,String keyStorePassword, String keyPassword,String address,String fromComp,String num,String days) {
		String result=new String("-1");
		BigInteger money=new BigInteger(num);
		BigInteger delays=new BigInteger(days);
		
		if (web3j == null ) {
			System.out.println("did not set web3j");
			return false;
		}
		Credentials credentials=loadkey(keyStoreFileName,keyStorePassword,keyPassword);
		if(credentials==null){
			System.out.println("did not set credentials");
			return false;
		}
		
		try {
			Transaction tran1=Transaction.load(address.toString(), web3j, credentials, new StaticGasProvider(gasPrice, gasLimited));
			String fromAddr=addressConf.get(fromComp);
			TransactionReceipt receipt=tran1.Buy(fromComp, money, delays).send();
			result=tran1.getBuyOutput(receipt).getValue1().toString();
			System.out.printf("%s have %s arrears now\n", fromComp,result);	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	//ask for financingHelp
	public Boolean askForHelp(String keyStoreFileName,String keyStorePassword, String keyPassword,String address,String num) {
		Boolean result=false;
		BigInteger asknum=new BigInteger(num);
		if (web3j == null ) {
			System.out.println("did not set web3j");
			return false;
		}
		Credentials credentials=loadkey(keyStoreFileName,keyStorePassword,keyPassword);
		if(credentials==null){
			System.out.println("did not set credentials");
			return false;
		}
		
		try {
			Transaction tran1=Transaction.load(address.toString(), web3j, credentials, new StaticGasProvider(gasPrice, gasLimited));
			tran1.newFinancingReq(asknum).send();
			String publicAddr=getPublicKey(keyStoreFileName,keyStorePassword,keyPassword);
			Credentials bankCred=loadkey("user.jks","20260805","20260805");
			Transaction theBank=Transaction.load(address.toString(), web3j, credentials, new StaticGasProvider(gasPrice, gasLimited));
			if(theBank!=null) {
				TransactionReceipt receipt=theBank.offerMoney(publicAddr).send();
				result=theBank.getOfferMoneyOutput(receipt).getValue1();
			}else {
				System.out.println("There is no bank!");
			}
			System.out.println("The bank accepts your financing request and provide you "+asknum);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	//return money to another company
	public Boolean returnToMoney(String keyStoreFileName,String keyStorePassword, String keyPassword,String address,String toComp,String money) {
		Boolean flag=false;
		
		if (web3j == null ) {
			System.out.println("did not set web3j");
			return false;
		}
		Credentials credentials=loadkey(keyStoreFileName,keyStorePassword,keyPassword);
		if(credentials==null){
			System.out.println("did not set credentials");
			return false;
		}
		
		try {
			Transaction tran1=Transaction.load(address.toString(), web3j, credentials, new StaticGasProvider(gasPrice, gasLimited));
			String toAddr=addressConf.get(toComp);
			if(toAddr==null) {
				System.out.printf("No such company: %s", toComp);
				return false;
			}
			tran1.returnMoney(addressConf.get(toComp),new BigInteger(money)).send();
			
			System.out.printf("Return %s to %s successfully!\n",toComp,money);
			flag=true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}
	
	//let another company forcely return back you money
	public Boolean rabMoney(String keyStoreFileName,String keyStorePassword, String keyPassword,String address,String fromComp) {
		Boolean flag=false;
		
		if (web3j == null ) {
			System.out.println("did not set web3j");
			return false;
		}
		Credentials credentials=loadkey(keyStoreFileName,keyStorePassword,keyPassword);
		if(credentials==null){
			System.out.println("did not set credentials");
			return false;
		}
		
		try {
			Transaction tran1=Transaction.load(address.toString(), web3j, credentials, new StaticGasProvider(gasPrice, gasLimited));
			String fromAddr=addressConf.get(fromComp);
			if(fromAddr==null) {
				System.out.printf("No such company: %s", fromComp);
				return false;
			}
			
			TransactionReceipt receipt=tran1.getMoney(fromAddr).send();
			String theReturn=tran1.getGetMoneyOutput(receipt).getValue1().toString();
			System.out.printf("You have got % forcely from %s\n" , theReturn,fromComp);
			flag=true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return flag;
	}
	
	 public Credentials loadkey(String keyStoreFileName,String keyStorePassword, String keyPassword) {
	    	InputStream ksInputStream = null;
	    	try {
	    		 KeyStore ks = KeyStore.getInstance("JKS");
	    		 ksInputStream =  BcosApp.class.getClassLoader().getResourceAsStream(keyStoreFileName);
	    		 ks.load(ksInputStream, keyStorePassword.toCharArray());
	    		 Key key = ks.getKey("ec", keyPassword.toCharArray());
	    		 ECKeyPair keyPair = ECKeyPair.create(((ECPrivateKey) key).getS());
	    		 Credentials credentials = Credentials.create(keyPair);	
	    		 if(credentials!=null){
	    		    return credentials;
	    		 }else{
	    			 System.out.println("��Կ������������");
	    		 }
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
				    if(null != ksInputStream)
				    {
					    ksInputStream.close();
				    }
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		    return null;
	    }
	    
	    public String getPublicKey(String keyStoreFileName,String keyStorePassword, String keyPassword) throws Exception{
	    	Credentials credentials=loadkey(keyStoreFileName, keyStorePassword, keyPassword);
	    	return credentials.getAddress();
	    }
	}
	
