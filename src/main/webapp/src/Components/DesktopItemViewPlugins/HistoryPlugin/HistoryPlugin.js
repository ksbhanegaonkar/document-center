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
      this.setState({historyData:data});
    });
    
  }


    render() {
      
        return <div className="history-plugin">

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
            
        </div>
      }

      renderTableData(){
          return this.state.historyData.map(row =>{
                return(
                    <tr>
                        <td>{row.version}</td>
                        <td>{row.updated_user}</td>
                        <td>{row.timestamp}</td>
                        <td>{row.comment}</td>
                        <td><botton>Download</botton></td>
                    </tr>
                );
          });
      }

}
export default HistoryPlugin;


