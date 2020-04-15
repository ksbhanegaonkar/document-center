import React,{Component} from 'react';
import './HistoryPlugin.css';
import {getRequest,postRequest,downloadFilePostRequest} from '../../Utils/RestUtil';
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
                        <td><button onClick={()=>this.downloadFile(row.version)}>Download</button></td>
                    </tr>
                );
          });
      }

      downloadFile(version){
        downloadFilePostRequest('/downloadapp',{appId: this.props.item.appId,version:version,appName:this.props.item.appName,option:"Download File"},
        (response) => {                
                  
                  const [r, e] = this.props.item.fileName.split(".");
                  let filename = r+"_v"+version+"."+e;
                  //let disposition = response.headers.get('Content-Disposition');
                  //console.log("Disposition is ::: "+disposition);
                      // let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                      // let matches = filenameRegex.exec(disposition);
                      // if (matches != null && matches[1]) { 
                      //   filename = matches[1].replace(/['"]/g, '');
                      // }

        
        
                  response.blob().then(blob => {
                    let url = window.URL.createObjectURL(blob);
                    let a = document.createElement('a');
                    a.href = url;
                    a.download = filename;
                    a.click();
                    });
             

        }
        );
      }

}
export default HistoryPlugin;


