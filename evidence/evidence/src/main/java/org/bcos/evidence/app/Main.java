package org.bcos.evidence.app;

import java.util.List;

import org.bcos.evidence.sample.EvidenceData;
import org.fisco.bcos.web3j.abi.datatypes.Address;


public class Main {
	
	private static String LOGO = "\n" 
            + "命令输入参考如下！ \n"
            + "工厂合约部署：./evidence deploy keyStoreFileName keyStorePassword keyPassword  \n"
            + "init Company：./evidence init keyStoreFileName keyStorePassword keyPassword aAddress CompN num  creRate \n"
            + "buy things：./evidence buy keyStoreFileName keyStorePassword keyPassword address fromComp num days \n"
            + "ask for financing request：./evidence request keyStoreFileName keyStorePassword keyPassword address num \n"
            + "return money：./evidence return keyStoreFileName keyStorePassword keyPassword address toComp money \n"
			+ "forcely get money: ./evidence forcelyget keyStoreFileName keyStorePassword keyPassword address fromComp"
            + "getPublicKey：./evidence getPublicKey keyStoreFileName keyStorePassword keyPassword  \n";

	public static void main(String[] args) throws Exception {
		//BcosApp app = new BcosApp();
		BussiApp app=new BussiApp();
		Address address=null;
		Address newEvidenceAddress=null;
		boolean configure = app.loadConfig();
		if(args.length<4)
		{
			System.out.println("输入参数最小为4");
			System.exit(0);
		}
		if (!configure) {
			System.err.println("error in load configure, init failed !!!");
			System.exit(0);
		}
		System.out.println(LOGO);
		switch (args[0]) {
			//deploy
		 	case "deploy":
		 		//此方法需要传入3个参数，参数1为keyStoreFileName（私钥文件名），参数2为keyStorePassword，参数3为keyPassword
		 		address=app.deployContract(args[1],args[2],args[3]);
		 		System.out.println("=========== deploy factoryContract success, address: " + address.toString()+"=========");
		 		break;
		 	//init company
		 	case "init":
		 		//传入7个参数，前三个分别1为keyStoreFileName（私钥文件名），参数2为keyStorePassword，参数3为keyPassword
		 		//传入参数需要步数的工厂合约地址，company的name,company的balance,company的creditRating
		 		//System.out.println("initCompany(String keyStoreFileName,String keyStorePassword,String keyPassword,String address,String CompN,String num,String creRate)");
		 		Boolean flag=app.initCompany(args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
		 		System.out.println("*******init company success! "+ flag+"*********");
		 		break;
		 		
		 	//buy things from another comp
		 	case "buy":
		 		//7个参数，1-keyStoreFileName（私钥文件名）,2-keyStorePassword,3-keyPassword
		 		//load合约的address,fromComp借钱公司，金额num，天数days
		 		//System.out.println("buySthFromAnoComp(String keyStoreFileName,String keyStorePassword, String keyPassword,String address,String fromComp,String num,String days)");
		 		Boolean flag1=app.buySthFromAnoComp(args[1],args[2], args[3], args[4], args[5], args[6], args[7]);
		 		if(flag1==true) {
		 			System.out.println("**********buy success!"+flag1+"************");
		 		}else {
		 			System.out.println("**********buy failed!"+flag1+"************");
		 		}
		 		break;
		 		
		 	//ask for financing request
		 	case "request":	
		 		//System.out.println("askForHelp(String keyStoreFileName,String keyStorePassword, String keyPassword,String address,String num)");
		 		Boolean flag2=app.askForHelp(args[1], args[2], args[3], args[4], args[5]);
		 		if(flag2) {
		 			System.out.println("*********finish handle your request!********");
		 		}else {
		 			System.out.println("*********your request is not allowed!*********");
		 		}
		 		break;
		 		
		 	//return money to another comp
		 	case "return":
		 		//System.out.println("returnToMoney(String keyStoreFileName,String keyStorePassword, String keyPassword,String address,String toComp,String money)");
		 		Boolean flag3=app.returnToMoney(args[1], args[2], args[3], args[4], args[5], args[6]);
		 		if(flag3) {
		 			System.out.println("**********return finished! success!*******");
		 		}else {
		 			System.out.println("**********return failed!******************");
		 		}
		 		break;
		 		
		 	//forcely get money from another comp
		 	case "forcelyget":
		 		//System.out.println("rabMoney(String keyStoreFileName,String keyStorePassword, String keyPassword,String address,String fromComp)");
		 		Boolean flag4=app.rabMoney(args[1], args[2], args[3], args[4], args[5]);
		 		if(flag4) {
		 			System.out.println("**********forcelyget money succeed!*********");
		 		}else {
		 			System.out.println("**********forcelyget money failed.**********");
		 		}
		 	break;
		 		
		 	
		 	case "getPublicKey":
		 		String publicKey=app.getPublicKey(args[1], args[2], args[3]);
		 		System.out.println("---------publicKey:"+publicKey);
		 		break;
		 	default:
                 break;
		}
		System.exit(0);
	}
	
}
