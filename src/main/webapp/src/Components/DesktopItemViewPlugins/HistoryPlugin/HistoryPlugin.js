import React,{Component} from 'react';
import './HistoryPlugin.css';
import {getRequest,postRequest} from '../../Utils/RestUtil';
class HistoryPlugin extends Component{

state={
 historyData:[],
 loading:true
};

  componentDidMount(){
    this.getPayload(this.props.item.appId);
  }


  getPayload(appId){
      this.setState({loading:true});
    getRequest('/getapphistory/'+appId,(data)=>{
        console.log("History data is...");
        console.dir(data);
      this.setState({historyData:data,loading:false});
    });
    
  }


    render() {


        if(this.state.loading){
            return (<div className='history-loading-message'>
                    <span>Loading history items...</span>
            </div>);
        }else{
           return (<div className="history-plugin">

            <table>
                <tbody>
                    <tr>
                        <th>Version</th>
                        <th>Updated By</th>
                        <th>Updated On</th>
                        <th>Comment</th>
                        <th>Download Link</th>
                    </tr>
                    {this.renderTableData()}
                </tbody>
            </table>
                
            </div>);
        }

      

      }

      renderTableData(){
          return this.state.historyData.map(row =>{
                return(
                    <tr>
                        <td>{row.version}</td>
                        <td>{row.updated_user}</td>
                        <td>{row.timestamp}</td>
                        <td>{row.comment}</td>
                        <td><button>Download</button></td>
                    </tr>
                );
          });
      }

}
export default HistoryPlugin;


