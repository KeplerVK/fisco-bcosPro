package org.bcos.evidence.web3j;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Transaction extends Contract {
    public static String BINARY = "608060405234801561001057600080fd5b506126cf806100206000396000f3006080604052600436106100c5576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680631cbc5ab1146100ca57806335bc88b11461013557806336429c731461018257806337e07e75146101dd5780634e5e097d1461020a578063717d55271461023557806376cdb03b1461028c57806381d12c58146102e3578063889cc70114610362578063bdd290b3146103c3578063db047c77146103da578063e30443bc146104a4578063f1e6fe8714610505575b600080fd5b3480156100d657600080fd5b5061011f600480360381019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803590602001909291905050506105ae565b6040518082815260200191505060405180910390f35b34801561014157600080fd5b50610180600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506110f3565b005b34801561018e57600080fd5b506101c3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611582565b604051808215151515815260200191505060405180910390f35b3480156101e957600080fd5b5061020860048036038101908080359060200190929190505050611a6a565b005b34801561021657600080fd5b5061021f611b9f565b6040518082815260200191505060405180910390f35b34801561024157600080fd5b50610276600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050611cf4565b6040518082815260200191505060405180910390f35b34801561029857600080fd5b506102a161213b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102ef57600080fd5b5061030e60048036038101908080359060200190929190505050612160565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200183815260200182151515158152602001935050505060405180910390f35b34801561036e57600080fd5b506103ad600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506121c6565b6040518082815260200191505060405180910390f35b3480156103cf57600080fd5b506103d86122b5565b005b3480156103e657600080fd5b5061041b600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506122f7565b6040518080602001848152602001838152602001828103825285818151815260200191508051906020019080838360005b8381101561046757808201518184015260208101905061044c565b50505050905090810190601f1680156104945780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b3480156104b057600080fd5b506104ef600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291905050506123b9565b6040518082815260200191505060405180910390f35b34801561051157600080fd5b5061056c600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506124a8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b60006105b8612599565b60008060008060003373ffffffffffffffffffffffffffffffffffffffff168a73ffffffffffffffffffffffffffffffffffffffff16141515156105fb57600080fd5b600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010180549050945060009350600092505b848310156106c657600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600101838154811015156106a357fe5b90600052602060002090600602016000015484019350828060010193505061064c565b8184101515610eaa57889150600090505b84811015610ea55781600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001018281548110151561072f57fe5b906000526020600020906006020160000154101515610a9457600160008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160c0604051908101604052808481526020018c73ffffffffffffffffffffffffffffffffffffffff168152602001600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001018481548110151561080757fe5b906000526020600020906006020160020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600101848154811015156108a357fe5b9060005260206000209060060201600301548152602001600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001018481548110151561090957fe5b906000526020600020906006020160040154815260200160001515815250908060018154018082558091505090600182039060005260206000209060060201600090919290919091506000820151816000015560208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff02191690831515021790555050505081600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010182815481101515610a7157fe5b906000526020600020906006020160000160008282540392505081905550610ea5565b600160008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160c060405190810160405280600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010184815481101515610b3157fe5b90600052602060002090600602016000015481526020018c73ffffffffffffffffffffffffffffffffffffffff168152602001600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010184815481101515610bb357fe5b906000526020600020906006020160020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010184815481101515610c4f57fe5b9060005260206000209060060201600301548152602001600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010184815481101515610cb557fe5b906000526020600020906006020160040154815260200160001515815250908060018154018082558091505090600182039060005260206000209060060201600090919290919091506000820151816000015560208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff0219169083151502179055505050506000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010182815481101515610e1e57fe5b906000526020600020906006020160000181905550600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010181815481101515610e8257fe5b9060005260206000209060060201600001548203915080806001019150506106d7565b61109d565b6005600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060020154111561109357600160008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160c0604051908101604052808b81526020018c73ffffffffffffffffffffffffffffffffffffffff1681526020013373ffffffffffffffffffffffffffffffffffffffff1681526020014281526020018a815260200160001515815250908060018154018082558091505090600182039060005260206000209060060201600090919290919091506000820151816000015560208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550606082015181600301556080820151816004015560a08201518160050160006101000a81548160ff02191690831515021790555050505061109c565b600096506110e6565b5b600160008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001018054905096505b5050505050509392505050565b600080600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600101805490509150600090505b8181101561157c57600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001018181548110151561119a57fe5b906000526020600020906006020160050160009054906101000a900460ff1615801561127157503373ffffffffffffffffffffffffffffffffffffffff16600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001018281548110151561122757fe5b906000526020600020906006020160020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b1561156f57600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600101818154811015156112c557fe5b9060005260206000209060060201600001548310156113f15782600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003016000828254019250508190555082600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003016000828254039250508190555082600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600101828154811015156113ce57fe5b90600052602060002090600602016000016000828254039250508190555061157c565b600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001018181548110151561144057fe5b9060005260206000209060060201600001548303925082600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003016000828254019250508190555082600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003016000828254039250508190555060018060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001018281548110151561154657fe5b906000526020600020906006020160050160006101000a81548160ff0219169083151502179055505b8080600101915050611143565b50505050565b6000806000806000806000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156115e957600080fd5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168973ffffffffffffffffffffffffffffffffffffffff161415151561164557600080fd5b60009650600095505b6002805490508610156117395760028681548110151561166a57fe5b906000526020600020906003020160020160009054906101000a900460ff1615801561170157508873ffffffffffffffffffffffffffffffffffffffff166002878154811015156116b757fe5b906000526020600020906003020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b1561172c5760028681548110151561171557fe5b906000526020600020906003020160010154870196505b858060010196505061164e565b6005600160008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206002015411156117dd5786600160008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003016000828254019250508190555060019750611a5e565b600160008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010180549050945060009350600092505b848310156118a857600160008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206001018381548110151561188557fe5b90600052602060002090600602016000015484019350828060010193505061182e565b60009150600160008a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060030154840191508684101515611a595786600160008b73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060030160008282540192505081905550600090505b600280549050811015611a505760028181548110151561196d57fe5b906000526020600020906003020160020160009054906101000a900460ff16158015611a0457508873ffffffffffffffffffffffffffffffffffffffff166002828154811015156119ba57fe5b906000526020600020906003020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b15611a43576001600282815481101515611a1a57fe5b906000526020600020906003020160020160006101000a81548160ff0219169083151502179055505b8080600101915050611951565b60019750611a5e565b600097505b50505050505050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151515611ac657600080fd5b60026060604051908101604052803373ffffffffffffffffffffffffffffffffffffffff168152602001838152602001600015158152509080600181540180825580915050906001820390600052602060002090600302016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015560408201518160020160006101000a81548160ff02191690831515021790555050505050565b600080600080600033935060009250600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600101805490509150600090505b81811015611cea57600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010181815481101515611c5257fe5b906000526020600020906006020160050160009054906101000a900460ff161515611cdd57600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010181815481101515611cc657fe5b906000526020600020906006020160000154830192505b8080600101915050611bfb565b8294505050505090565b600080600080600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010180549050925060009150600090505b8281101561213057600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010181815481101515611da257fe5b90600052602060002090600602016000015482019150600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010181815481101515611e0757fe5b906000526020600020906006020160050160009054906101000a900460ff16158015611ef9575062015180600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010182815481101515611e8157fe5b90600052602060002090600602016004015402600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010182815481101515611ee357fe5b9060005260206000209060060201600301540142115b8015611fb057508473ffffffffffffffffffffffffffffffffffffffff16600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010182815481101515611f6657fe5b906000526020600020906006020160020160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16145b156121235781600160008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003015410151561211d5781600160008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003016000828254039250508190555081600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206003016000828254019250508190555060018060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600101828154811015156120f057fe5b906000526020600020906006020160050160006101000a81548160ff021916908315150217905550612122565b612130565b5b8080600101915050611d4b565b819350505050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60028181548110151561216f57fe5b90600052602060002090600302016000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154908060020160009054906101000a900460ff16905083565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561222357600080fd5b81600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060020181905550600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060020154905092915050565b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550565b6001602052806000526040600020600091509050806000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156123a35780601f10612378576101008083540402835291602001916123a3565b820191906000526020600020905b81548152906001019060200180831161238657829003601f168201915b5050505050908060020154908060030154905083565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561241657600080fd5b81600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060030181905550600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060030154905092915050565b600081600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000190805190602001906125009291906125fe565b506000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600201819055506000600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060030181905550339050919050565b60c06040519081016040528060008152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001600073ffffffffffffffffffffffffffffffffffffffff16815260200160008152602001600081526020016000151581525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061263f57805160ff191683800117855561266d565b8280016001018555821561266d579182015b8281111561266c578251825591602001919060010190612651565b5b50905061267a919061267e565b5090565b6126a091905b8082111561269c576000816000905550600101612684565b5090565b905600a165627a7a723058201a8ff6d64635f5aa27529ccd7b1dbabc472483c6095de9b110f36ce9f0267dc00029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"fromComp\",\"type\":\"address\"},{\"name\":\"nums\",\"type\":\"uint256\"},{\"name\":\"returnDay\",\"type\":\"uint256\"}],\"name\":\"Buy\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"Comp\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"returnMoney\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"Comp\",\"type\":\"address\"}],\"name\":\"offerMoney\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"money\",\"type\":\"uint256\"}],\"name\":\"newFinancingReq\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getArrears\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"Comp\",\"type\":\"address\"}],\"name\":\"getMoney\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"bank\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"requests\",\"outputs\":[{\"name\":\"company\",\"type\":\"address\"},{\"name\":\"requestMoney\",\"type\":\"uint256\"},{\"name\":\"offered\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"Comp\",\"type\":\"address\"},{\"name\":\"rate\",\"type\":\"uint256\"}],\"name\":\"setCreditRating\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"setBank\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"address\"}],\"name\":\"companys\",\"outputs\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"creditRating\",\"type\":\"uint256\"},{\"name\":\"balances\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"Comp\",\"type\":\"address\"},{\"name\":\"balanceAmmount\",\"type\":\"uint256\"}],\"name\":\"setBalance\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"name\",\"type\":\"string\"}],\"name\":\"newCompany\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_BUY = "Buy";

    public static final String FUNC_RETURNMONEY = "returnMoney";

    public static final String FUNC_OFFERMONEY = "offerMoney";

    public static final String FUNC_NEWFINANCINGREQ = "newFinancingReq";

    public static final String FUNC_GETARREARS = "getArrears";

    public static final String FUNC_GETMONEY = "getMoney";

    public static final String FUNC_BANK = "bank";

    public static final String FUNC_REQUESTS = "requests";

    public static final String FUNC_SETCREDITRATING = "setCreditRating";

    public static final String FUNC_SETBANK = "setBank";

    public static final String FUNC_COMPANYS = "companys";

    public static final String FUNC_SETBALANCE = "setBalance";

    public static final String FUNC_NEWCOMPANY = "newCompany";

    @Deprecated
    protected Transaction(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Transaction(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Transaction(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Transaction(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public RemoteCall<TransactionReceipt> Buy(String fromComp, BigInteger nums, BigInteger returnDay) {
        final Function function = new Function(
                FUNC_BUY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(fromComp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nums), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(returnDay)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void Buy(String fromComp, BigInteger nums, BigInteger returnDay, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_BUY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(fromComp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nums), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(returnDay)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String BuySeq(String fromComp, BigInteger nums, BigInteger returnDay) {
        final Function function = new Function(
                FUNC_BUY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(fromComp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(nums), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(returnDay)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple3<String, BigInteger, BigInteger> getBuyInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_BUY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple3<String, BigInteger, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue()
                );
    }

    public Tuple1<BigInteger> getBuyOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_BUY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> returnMoney(String Comp, BigInteger value) {
        final Function function = new Function(
                FUNC_RETURNMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void returnMoney(String Comp, BigInteger value, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_RETURNMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String returnMoneySeq(String Comp, BigInteger value) {
        final Function function = new Function(
                FUNC_RETURNMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getReturnMoneyInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_RETURNMONEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> offerMoney(String Comp) {
        final Function function = new Function(
                FUNC_OFFERMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void offerMoney(String Comp, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_OFFERMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String offerMoneySeq(String Comp) {
        final Function function = new Function(
                FUNC_OFFERMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getOfferMoneyInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_OFFERMONEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<Boolean> getOfferMoneyOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_OFFERMONEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> newFinancingReq(BigInteger money) {
        final Function function = new Function(
                FUNC_NEWFINANCINGREQ, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(money)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void newFinancingReq(BigInteger money, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_NEWFINANCINGREQ, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(money)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String newFinancingReqSeq(BigInteger money) {
        final Function function = new Function(
                FUNC_NEWFINANCINGREQ, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(money)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<BigInteger> getNewFinancingReqInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_NEWFINANCINGREQ, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> getArrears() {
        final Function function = new Function(
                FUNC_GETARREARS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void getArrears(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_GETARREARS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getArrearsSeq() {
        final Function function = new Function(
                FUNC_GETARREARS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<BigInteger> getGetArrearsOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_GETARREARS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> getMoney(String Comp) {
        final Function function = new Function(
                FUNC_GETMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void getMoney(String Comp, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_GETMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getMoneySeq(String Comp) {
        final Function function = new Function(
                FUNC_GETMONEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getGetMoneyInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_GETMONEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getGetMoneyOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_GETMONEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public RemoteCall<String> bank() {
        final Function function = new Function(FUNC_BANK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple3<String, BigInteger, Boolean>> requests(BigInteger param0) {
        final Function function = new Function(FUNC_REQUESTS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple3<String, BigInteger, Boolean>>(
                new Callable<Tuple3<String, BigInteger, Boolean>>() {
                    @Override
                    public Tuple3<String, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> setCreditRating(String Comp, BigInteger rate) {
        final Function function = new Function(
                FUNC_SETCREDITRATING, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(rate)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setCreditRating(String Comp, BigInteger rate, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETCREDITRATING, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(rate)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String setCreditRatingSeq(String Comp, BigInteger rate) {
        final Function function = new Function(
                FUNC_SETCREDITRATING, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(rate)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getSetCreditRatingInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETCREDITRATING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public Tuple1<BigInteger> getSetCreditRatingOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SETCREDITRATING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> setBank() {
        final Function function = new Function(
                FUNC_SETBANK, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setBank(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETBANK, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String setBankSeq() {
        final Function function = new Function(
                FUNC_SETBANK, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple3<String, BigInteger, BigInteger>> companys(String param0) {
        final Function function = new Function(FUNC_COMPANYS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<String, BigInteger, BigInteger>>(
                new Callable<Tuple3<String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> setBalance(String Comp, BigInteger balanceAmmount) {
        final Function function = new Function(
                FUNC_SETBALANCE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balanceAmmount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setBalance(String Comp, BigInteger balanceAmmount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETBALANCE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balanceAmmount)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String setBalanceSeq(String Comp, BigInteger balanceAmmount) {
        final Function function = new Function(
                FUNC_SETBALANCE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(Comp), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(balanceAmmount)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getSetBalanceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public Tuple1<BigInteger> getSetBalanceOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SETBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> newCompany(String name) {
        final Function function = new Function(
                FUNC_NEWCOMPANY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void newCompany(String name, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_NEWCOMPANY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String newCompanySeq(String name) {
        final Function function = new Function(
                FUNC_NEWCOMPANY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(name)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getNewCompanyInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_NEWCOMPANY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<String> getNewCompanyOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_NEWCOMPANY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    @Deprecated
    public static Transaction load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Transaction(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Transaction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Transaction(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Transaction load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Transaction(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Transaction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Transaction(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Transaction> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Transaction.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Transaction> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Transaction.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Transaction> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Transaction.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Transaction> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Transaction.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
