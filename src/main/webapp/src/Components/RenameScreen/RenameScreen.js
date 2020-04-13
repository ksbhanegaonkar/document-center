import React,{Component} from 'react';
import './RenameScreen.scss';
import {postRequest,getRequest} from '../Utils/RestUtil';
class RenameScreen extends Component{

    state={
        newName:'',
        errorMsg:''
    };

    render() {
        var style={
          display:this.props.isRename?'block':'none',
        };
        console.log("inside rename screen..."+this.props.isRename);
        const [app, type, id, name] = this.props.appToRename.split("/");
        return (<div className="rename-screen" style={style}>
            <div className="rename-pannel">
            <div>Renaming {name}
                <button onClick={() => this.copyCurrentName(name)}>Copy current name</button>
            </div> 
                <input type="text" value={this.state.newName} onChange={this.updateName.bind(this)}></input>
                <button onClick={()=>this.rename(id,this.state.newName,this.props.parentAppId)}>Rename</button>
                <button onClick={()=>this.cancel()}>Cancel</button>
                
                <div className='rename-error-message'>
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
            // const [app, type, id, name] = this.props.appToRename.split("/");
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
            this.props.doneRename();
            this.setState({newName:"",errorMsg:""});
        }

}
export default RenameScreen;