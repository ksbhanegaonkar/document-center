import React,{Component} from 'react';
import './HistoryPlugin.css';
import {getRequest,postRequest} from '../../Utils/RestUtil';
class HistoryPlugin extends Component{

state={
 historyData:[]
};

  componentDidMount(){
    this.getPayload(this.props.item.appId);
  }


  getPayload(appId){
    getRequest('/getapphistory/'+appId,(data)=>{
        console.log("History data is...");
        console.dir(data);
      this.setState({historyData:data.payload});
    });
    
  }


    render() {
      
        return <div className="history-plugin">


            
        </div>
      }



}
export default HistoryPlugin;


