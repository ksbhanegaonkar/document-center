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
                <button onClick={()=>this.uploadFile(id,this.state.newFile,this.state.comment)}>Update</button>
                <button onClick={()=>this.cancel()}>Cancel</button>
                
                <div className='update-app-error-message'>
                     <span>{this.state.errorMsg}</span>
                </div>
            </div>



        </div>)
      }


      componentDidMount(){

        }
    

      rename(appId,newName,parentAppId){

            postRequest("/renameapp",{appId:appId,newName:newName,parentAppId:parentAppId},(data)=>{
                console.log("rename returned data is ::: "+data.isSuccess);
                console.dir(data);
                if(data.isSuccess){
                    this.props.doneRename();
                    this.setState({newName:"",errorMsg:""});
                }else{
                    this.setState({errorMsg:"Name already exist, please use different name !!!"})
                }
                
            });
            
        }

        componentWillUpdate(){
            // const [app, type, id, name] = this.props.appToUpdate.split("/");
            // console.log("name is ::::  "+name);
            // //this.state.newName=name;
        }

        copyCurrentName(name){
            this.setState({newName:name});
        }

        updateName(e){
            this.setState({newName:e.target.value});
        }

        cancel(){
            this.props.cancleAppUpdate();
            //this.setState({newName:"",errorMsg:""});
        }


        uploadFile = async (appId,newFile,comment) => {
            const formData = new FormData();
            formData.append('file',newFile);
            formData.append('appId', appId);
            formData.append('comment', comment);
            uploadFilePostRequest("/updateappversion",formData,(data)=>{
                console.log("hello");
            });
          }

          addFile(e){
              console.dir(e.target.files[0]);
              this.setState({newFile:e.target.files[0]});
          }

          addComment(e){
              console.log(e.target.value);
              this.setState({comment:e.target.value});
          }

}
export default UpdateAppScreen;