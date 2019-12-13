pragma solidity ^0.4.2;

contract transaction{
  //债权
    struct Credit{
        uint Arrearsamount;//金额
        address creditor;//债权人
        address debtor; //债务人
        uint startTime;//生效时间
        uint keepDays;//持有天数
        bool isReturn;//是否还款
    }
    
  //公司
    struct Company{
        string name;//公司名
        Credit[] credits;//债权,可能多个
        uint creditRating;//信用等级
        uint balances;//企业余额
    }
    
   //融资申请
    struct financingRequest{
        address company;
        uint requestMoney;
        bool offered;//是否提供
    }
        
    //银行，第三方可信机构
    address public bank;
    
    //状态变量，为每个可能的地址存储一个`Company`
    mapping(address=>Company) public companys;
    
    //融资申请的动态数组
    financingRequest[]public requests;

     event send_event(address addr1, address addr2, uint256 nu);
    //设置第三方银行为信息发送者
    function setBank() {
        bank=msg.sender;
    }
    
     //初始化企业
    function newCompany(string name) public returns(address){
        companys[msg.sender].name=name;
        companys[msg.sender].creditRating=0;
        companys[msg.sender].balances=0;
        return msg.sender;
    }
    
    //银行对企业进行信用评级
    function setCreditRating(address Comp,uint rate) public returns(uint) {
        //信用由银行设置
        require(msg.sender==bank);
        companys[Comp].creditRating=rate;
        return companys[Comp].creditRating;
    }
    
    //为某个企业设置金额
    function setBalance(address Comp, uint balanceAmmount) public returns(uint){
        //企业金额由银行设置
        require(msg.sender==bank);
        companys[Comp].balances=balanceAmmount;
        return companys[Comp].balances;
    }
    

    //功能一功能二、采购商品以及应收账款的转让上链
    //如有债权则优先进行债权转让
    //如果没有则判断自己的信用等级是否能够签订单据
    //可以，则签订单据；不可以，则现金支付
    function Buy (address fromComp,uint nums,uint returnDay)public returns(uint){
       //从那里购买物资的公司不能和发出操作的公司是同一个
        require(fromComp!=msg.sender);
        Credit memory cre;
        
        //公司拥有的债权的数量
        uint len=companys[msg.sender].credits.length;
        
        //可转让债权总额
        uint transAmount=0;
        
        //计算可转让债权总额
        for(uint i=0;i<len;i++){
            transAmount+= companys[msg.sender].credits[i].Arrearsamount;
        }
        
        //若额度够，则进行转让
        if(transAmount>=num){
          uint num=nums;
          for(uint j=0;j<len;j++){
             //若当前债权额度大于要求，则部分转让
             //将转让部分加入fromComp的credits队列
            if(companys[msg.sender].credits[j].Arrearsamount>=num){
                companys[fromComp].credits.push(Credit({
                    Arrearsamount:num,
                    creditor:fromComp,
                    debtor:companys[msg.sender].credits[j].debtor,
                    startTime:companys[msg.sender].credits[j].startTime,
                    keepDays:companys[msg.sender].credits[j].keepDays,
                    isReturn:false
                }));
            //将转让部分从msg.sender的credits队列中减去
                companys[msg.sender].credits[j].Arrearsamount-=num;
                break;
            }else{
                //若当前债权额度小于要求，则全部转让
                companys[fromComp].credits.push(Credit({
                    Arrearsamount:companys[msg.sender].credits[j].Arrearsamount,
                    creditor:fromComp,
                    debtor:companys[msg.sender].credits[j].debtor,
                    startTime:companys[msg.sender].credits[j].startTime,
                    keepDays:companys[msg.sender].credits[j].keepDays,
                    isReturn:false
                }));
                companys[msg.sender].credits[j].Arrearsamount=0;
                num-=companys[msg.sender].credits[j].Arrearsamount;
            }
          }
        }else{
            //如果自己的信用等级能够签订单据,则自己签订单据向fromComp欠款
            if(companys[msg.sender].creditRating>5){
                companys[fromComp].credits.push(Credit({
                    Arrearsamount:nums,
                    creditor:fromComp,
                    debtor:msg.sender,
                    startTime:now,
                    keepDays:returnDay,
                    isReturn:false
                }));
            }else{
                //如果不能签订单据，return
                return 0;
            }
        }
             
        return companys[fromComp].credits.length;
    }
    
    
    
    //功能三、利用应收账款向银行融资上链
    //如果信用等级到达标准，可以直接融资
    //如果等级没达到标准
    //如果债权金额总和加上公司已有金额总和大于所需融资总金额，则提供融资
    //否则不同意融资
    
    //首先编写一个融资需求操作
    function newFinancingReq(uint money) {
        //不能是银行操作
        require(msg.sender!=bank);
        //创建一个新的financingRequest对象并把它添加到融资申请队列末尾
        requests.push(
            financingRequest({
              company:msg.sender,
              requestMoney:money,
              offered:false
        })
      );
    }
    
    //银行根据应收账款提供融资
    function offerMoney(address Comp) public returns(bool){
        require(msg.sender==bank);
        require(Comp!=bank);
        
        //所需融资总数
        uint requestAmount=0;
        
        //查看所有该申请者的申请求出所需融资总金额
        for(uint i=0;i<requests.length;i++){
            if(!requests[i].offered && requests[i].company==Comp){
                requestAmount+=requests[i].requestMoney;
            }
        }
        
        //如果信用等级到达标准，可以直接融资
        if(companys[Comp].creditRating>5){
            companys[Comp].balances+=requestAmount;
            return true;
        }
        
        //如果等级没达到标准
        //如果债权金额总和加上公司已有金额总和大于所需融资总金额，则提供融资
        //否则不同意融资
        uint len=companys[Comp].credits.length;
        uint creditAmmount=0;
        
        for(uint j=0;j<len;j++){
            creditAmmount+=companys[Comp].credits[j].Arrearsamount;
        }
        
        //计算可转让债权总额与公司已有金额的总和
        uint existAmmount=0;
        existAmmount=creditAmmount+companys[Comp].balances;
        
        //大于等于,提供
        if(creditAmmount>=requestAmount){
            companys[Comp].balances+=requestAmount;
            for(uint a=0;a<requests.length;a++){
                if(!requests[a].offered && requests[a].company==Comp){
                    requests[a].offered=true;
                }
            }
            return true;
        }else{
            return false;
        }
    }
    
    
    
    //功能四、应收账款支付结算上链
    //债务人可以随时还钱
    //如债务人未在还钱日期之前还钱，则债权人可讨债
    
    //msg.sender偿还目标Comp的钱
    function returnMoney(address Comp,uint value){
        uint len=companys[Comp].credits.length;
    
        for(uint i=0;i<len;i++){
            if(!companys[Comp].credits[i].isReturn && companys[Comp].credits[i].debtor==msg.sender){
                //value小于债务，则value全部偿还
                if(value < companys[Comp].credits[i].Arrearsamount){
                    companys[Comp].balances+=value;
                    companys[msg.sender].balances-=value;
                    companys[Comp].credits[i].Arrearsamount-=value;
                    break;
                }else{
                    //value大于等于债务,只需要还欠的金额
                    value-=companys[Comp].credits[i].Arrearsamount;
                    companys[Comp].balances+=value;
                    companys[msg.sender].balances-=value;
                    companys[Comp].credits[i].isReturn=true;
                }
            }
        }
    }
    
    //msg.sender向目标Comp讨债
    function getMoney(address Comp) returns(uint){
        uint len=companys[msg.sender].credits.length;
        //Comp应偿还金额
        uint account=0;
        
        for(uint i=0;i<len;i++){
            account+=companys[msg.sender].credits[i].Arrearsamount;
            if(!companys[msg.sender].credits[i].isReturn && 
            now>(companys[msg.sender].credits[i].startTime+
            companys[msg.sender].credits[i].keepDays* 1 days) &&
            companys[msg.sender].credits[i].debtor==Comp){
                //如果Comp的balances大于等于应偿还金额，讨债成功
                if(companys[Comp].balances>=account){
                    companys[Comp].balances-=account;
                    companys[msg.sender].balances+=account;
                    companys[msg.sender].credits[i].isReturn=true;
                }else{
                    //否则，讨债失败
                    break;
                }
            }
        }
        return account;
    }
    
    // 获取当前本公司拥有的债权
    function getArrears() returns(uint){
     address company=msg.sender;
     uint result=0;
     uint len=companys[company].credits.length;
     for(uint i=0;i<len;i++){
         if(!companys[company].credits[i].isReturn){
             result+=companys[company].credits[i].Arrearsamount;
         }
     }
     return result;
 }
     
 
    
}