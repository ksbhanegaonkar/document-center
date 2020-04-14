import React,{Component} from 'react';
import './UpdateAppScreen.css';
import {postRequest,getRequest,downloadFilePostRequest,uploadFilePostRequest} from '../Utils/RestUtil';
class UpdateAppScreen extends Component{

    state={
        newName:'',
        errorMsg:'',
        newFile:'',
        comment:'',
        successMsg:'',
        loadingMsg:''
    };

    render() {
        var style={
          display:this.props.isAppUpdate?'block':'none',
        };



            const [app, type, id, name] = this.props.appToUpdate.split("/");
            return (<div className="update-app-screen" style={style}>
                <div className="update-app-pannel">
                <div>Updating Version for  {name}</div> 
                    <input type="file" disabled={this.state.successMsg || this.state.loadingMsg} ref="fileUpdator" onChange={this.addFile.bind(this)}></input>
                    <br></br>
                    Add Comment :
                    <br></br>
                    <textarea disabled={this.state.successMsg || this.state.loadingMsg} rows="4" cols="50" name="comment" value={this.state.comment} onChange={this.addComment.bind(this)}/>
                    <br></br>
                    <button disabled={this.state.successMsg || this.state.loadingMsg}
                     onClick={()=>this.uploadFile(id,name,this.state.newName,this.state.newFile,this.state.comment)}>Update</button>
                    <button onClick={()=>this.close()}>Close</button>
                    
                    <div className='update-app-error-message'>
                         <span>{this.state.errorMsg}</span>
                    </div>

                    <div className='update-app-success-message'>
                         <span>{this.state.successMsg}</span>
                    </div>

                    <div className='update-app-loading-message'>
                         <span>{this.state.loadingMsg}</span>
                    </div>
                </div>
    
    
    
            </div>);
            
        

      }

      renderButtons(){

      }





      close(){
            this.refs.fileUpdator.value=null;
            this.setState({newName:"",errorMsg:"",newFile:"",comment:"",successMsg:""});
            this.props.cancleAppUpdate();
        }


        uploadFile = async (appId,currentFileName,newFileName,newFile,comment) => {
            console.dir(newFile);
            if(newFile === ''){
                this.setState({errorMsg:"Please add new file...!"});
            }else if(currentFileName !== newFileName){
                this.setState({errorMsg:"Newly added file is not same as existing...!"});
            }else if(comment === ''){
                this.setState({errorMsg:"Please add comment...!"});
            }else{
                this.setState({errorMsg:'',loadingMsg:"Updating..."});
                const formData = new FormData();
                formData.append('file',newFile);
                formData.append('appId', appId);
                formData.append('comment', comment);
                uploadFilePostRequest("/updateappversion",formData,(data)=>{
                    this.setState({successMsg:"Updated Successfully...!",loadingMsg:""});
                });
            }
          }

          addFile(e){
              if(e.target.files.length != 0){
                this.setState({newFile:e.target.files[0],newName:e.target.files[0].name});
              }
          }

          addComment(e){
              console.log(e.target.value);
              this.setState({comment:e.target.value});
          }

}
export default UpdateAppScreen;