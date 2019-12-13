pragma solidity ^0.4.2;

contract transaction{
  //ծȨ
    struct Credit{
        uint Arrearsamount;//���
        address creditor;//ծȨ��
        address debtor; //ծ����
        uint startTime;//��Чʱ��
        uint keepDays;//��������
        bool isReturn;//�Ƿ񻹿�
    }
    
  //��˾
    struct Company{
        string name;//��˾��
        Credit[] credits;//ծȨ,���ܶ��
        uint creditRating;//���õȼ�
        uint balances;//��ҵ���
    }
    
   //��������
    struct financingRequest{
        address company;
        uint requestMoney;
        bool offered;//�Ƿ��ṩ
    }
        
    //���У����������Ż���
    address public bank;
    
    //״̬������Ϊÿ�����ܵĵ�ַ�洢һ��`Company`
    mapping(address=>Company) public companys;
    
    //��������Ķ�̬����
    financingRequest[]public requests;

     event send_event(address addr1, address addr2, uint256 nu);
    //���õ���������Ϊ��Ϣ������
    function setBank() {
        bank=msg.sender;
    }
    
     //��ʼ����ҵ
    function newCompany(string name) public returns(address){
        companys[msg.sender].name=name;
        companys[msg.sender].creditRating=0;
        companys[msg.sender].balances=0;
        return msg.sender;
    }
    
    //���ж���ҵ������������
    function setCreditRating(address Comp,uint rate) public returns(uint) {
        //��������������
        require(msg.sender==bank);
        companys[Comp].creditRating=rate;
        return companys[Comp].creditRating;
    }
    
    //Ϊĳ����ҵ���ý��
    function setBalance(address Comp, uint balanceAmmount) public returns(uint){
        //��ҵ�������������
        require(msg.sender==bank);
        companys[Comp].balances=balanceAmmount;
        return companys[Comp].balances;
    }
    

    //����һ���ܶ����ɹ���Ʒ�Լ�Ӧ���˿��ת������
    //����ծȨ�����Ƚ���ծȨת��
    //���û�����ж��Լ������õȼ��Ƿ��ܹ�ǩ������
    //���ԣ���ǩ�����ݣ������ԣ����ֽ�֧��
    function Buy (address fromComp,uint nums,uint returnDay)public returns(uint){
       //�����ﹺ�����ʵĹ�˾���ܺͷ��������Ĺ�˾��ͬһ��
        require(fromComp!=msg.sender);
        Credit memory cre;
        
        //��˾ӵ�е�ծȨ������
        uint len=companys[msg.sender].credits.length;
        
        //��ת��ծȨ�ܶ�
        uint transAmount=0;
        
        //�����ת��ծȨ�ܶ�
        for(uint i=0;i<len;i++){
            transAmount+= companys[msg.sender].credits[i].Arrearsamount;
        }
        
        //����ȹ��������ת��
        if(transAmount>=num){
          uint num=nums;
          for(uint j=0;j<len;j++){
             //����ǰծȨ��ȴ���Ҫ���򲿷�ת��
             //��ת�ò��ּ���fromComp��credits����
            if(companys[msg.sender].credits[j].Arrearsamount>=num){
                companys[fromComp].credits.push(Credit({
                    Arrearsamount:num,
                    creditor:fromComp,
                    debtor:companys[msg.sender].credits[j].debtor,
                    startTime:companys[msg.sender].credits[j].startTime,
                    keepDays:companys[msg.sender].credits[j].keepDays,
                    isReturn:false
                }));
            //��ת�ò��ִ�msg.sender��credits�����м�ȥ
                companys[msg.sender].credits[j].Arrearsamount-=num;
                break;
            }else{
                //����ǰծȨ���С��Ҫ����ȫ��ת��
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
            //����Լ������õȼ��ܹ�ǩ������,���Լ�ǩ��������fromCompǷ��
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
                //�������ǩ�����ݣ�return
                return 0;
            }
        }
             
        return companys[fromComp].credits.length;
    }
    
    
    
    //������������Ӧ���˿���������������
    //������õȼ������׼������ֱ������
    //����ȼ�û�ﵽ��׼
    //���ծȨ����ܺͼ��Ϲ�˾���н���ܺʹ������������ܽ����ṩ����
    //����ͬ������
    
    //���ȱ�дһ�������������
    function newFinancingReq(uint money) {
        //���������в���
        require(msg.sender!=bank);
        //����һ���µ�financingRequest���󲢰�����ӵ������������ĩβ
        requests.push(
            financingRequest({
              company:msg.sender,
              requestMoney:money,
              offered:false
        })
      );
    }
    
    //���и���Ӧ���˿��ṩ����
    function offerMoney(address Comp) public returns(bool){
        require(msg.sender==bank);
        require(Comp!=bank);
        
        //������������
        uint requestAmount=0;
        
        //�鿴���и������ߵ�����������������ܽ��
        for(uint i=0;i<requests.length;i++){
            if(!requests[i].offered && requests[i].company==Comp){
                requestAmount+=requests[i].requestMoney;
            }
        }
        
        //������õȼ������׼������ֱ������
        if(companys[Comp].creditRating>5){
            companys[Comp].balances+=requestAmount;
            return true;
        }
        
        //����ȼ�û�ﵽ��׼
        //���ծȨ����ܺͼ��Ϲ�˾���н���ܺʹ������������ܽ����ṩ����
        //����ͬ������
        uint len=companys[Comp].credits.length;
        uint creditAmmount=0;
        
        for(uint j=0;j<len;j++){
            creditAmmount+=companys[Comp].credits[j].Arrearsamount;
        }
        
        //�����ת��ծȨ�ܶ��빫˾���н����ܺ�
        uint existAmmount=0;
        existAmmount=creditAmmount+companys[Comp].balances;
        
        //���ڵ���,�ṩ
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
    
    
    
    //�����ġ�Ӧ���˿�֧����������
    //ծ���˿�����ʱ��Ǯ
    //��ծ����δ�ڻ�Ǯ����֮ǰ��Ǯ����ծȨ�˿���ծ
    
    //msg.sender����Ŀ��Comp��Ǯ
    function returnMoney(address Comp,uint value){
        uint len=companys[Comp].credits.length;
    
        for(uint i=0;i<len;i++){
            if(!companys[Comp].credits[i].isReturn && companys[Comp].credits[i].debtor==msg.sender){
                //valueС��ծ����valueȫ������
                if(value < companys[Comp].credits[i].Arrearsamount){
                    companys[Comp].balances+=value;
                    companys[msg.sender].balances-=value;
                    companys[Comp].credits[i].Arrearsamount-=value;
                    break;
                }else{
                    //value���ڵ���ծ��,ֻ��Ҫ��Ƿ�Ľ��
                    value-=companys[Comp].credits[i].Arrearsamount;
                    companys[Comp].balances+=value;
                    companys[msg.sender].balances-=value;
                    companys[Comp].credits[i].isReturn=true;
                }
            }
        }
    }
    
    //msg.sender��Ŀ��Comp��ծ
    function getMoney(address Comp) returns(uint){
        uint len=companys[msg.sender].credits.length;
        //CompӦ�������
        uint account=0;
        
        for(uint i=0;i<len;i++){
            account+=companys[msg.sender].credits[i].Arrearsamount;
            if(!companys[msg.sender].credits[i].isReturn && 
            now>(companys[msg.sender].credits[i].startTime+
            companys[msg.sender].credits[i].keepDays* 1 days) &&
            companys[msg.sender].credits[i].debtor==Comp){
                //���Comp��balances���ڵ���Ӧ��������ծ�ɹ�
                if(companys[Comp].balances>=account){
                    companys[Comp].balances-=account;
                    companys[msg.sender].balances+=account;
                    companys[msg.sender].credits[i].isReturn=true;
                }else{
                    //������ծʧ��
                    break;
                }
            }
        }
        return account;
    }
    
    // ��ȡ��ǰ����˾ӵ�е�ծȨ
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