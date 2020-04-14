import React,{Component} from 'react';
import './UpdateAppScreen.css';
import {postRequest,getRequest,downloadFilePostRequest,uploadFilePostRequest} from '../Utils/RestUtil';
class UpdateAppScreen extends Component{

    state={
        newName:'',
        errorMsg:'',
        newFile:'',
        comment:''
    };

    render() {
        var style={
          display:this.props.isAppUpdate?'block':'none',
        };
        const [app, type, id, name] = this.props.appToUpdate.split("/");
        return (<div className="update-app-screen" style={style}>
            <div className="update-app-pannel">
            <div>Updating Version for  {name}</div> 
                <input type="file" onChange={this.addFile.bind(this)}></input>
                <br></br>
                Add Comment :
                <br></br>
                <textarea rows="4" cols="50" name="comment" onChange={this.addComment.bind(this)}/>
                <br></br>
                <button onClick={()=>this.uploadFile(id,name,this.state.newName,this.state.newFile,this.state.comment)}>Update</button>
                <button onClick={()=>this.cancel()}>Cancel</button>
                
                <div className='update-app-error-message'>
                     <span>{this.state.errorMsg}</span>
                </div>
            </div>



        </div>)
      }






        cancel(){
            this.props.cancleAppUpdate();
            this.setState({newName:"",errorMsg:""});
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
                this.setState({errorMsg:''});
                const formData = new FormData();
                formData.append('file',newFile);
                formData.append('appId', appId);
                formData.append('comment', comment);
                uploadFilePostRequest("/updateappversion",formData,(data)=>{
                    console.log("hello");
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