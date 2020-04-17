import React,{Component} from 'react';
import './UserConsolePlugin.css';
import {getRequest,postRequest} from '../../Utils/RestUtil';

class UserConsolePlugin extends Component{

    state={
        userName:"",
        email:"",
        successMsg:'',
        errorMsg:''
    }
  componentDidMount(){
    //this.getPayload(this.props.item.appId);
  }
    render() {


            return (<div className='user-console'>
                        <label className="user-console-label">User Name :</label>
                        <input className="user-console-input" type="text" onChange={this.setUserName.bind(this)} placeholder="User Name" name="username" required/>
                        <br></br>
                        <label className="user-console-label">Email :</label>
                        <input className="user-console-input" type="text" onChange={this.setEmail.bind(this)} placeholder="Enter Email" name="email" required/>
                        <br></br>
                        <button onClick={this.addUser.bind(this)}>Add User</button>
                        <br></br>
    
                        <div className="user-added-success-message">
                            <span>{this.state.successMsg}</span>
                        </div>
                         <div className="user-added-error-message">
                            <span>{this.state.errorMsg}</span>
                        </div>
                     </div>);
        }

    
        setUserName(e){
            this.setState({userName:e.target.value});
        }

        setEmail(e){
            this.setState({email:e.target.value});
        }

        addUser(){
            if(this.state.userName===""){
                this.setState({errorMsg:"Please add user name...!"});
            }else if(this.state.email===""){
                this.setState({errorMsg:"Please add email name...!"});
            }else{
            postRequest("/adduser",{username:this.state.userName,email:this.state.email},
            (data)=>{
                this.setState({successMsg:"User added successfully...!"});
            });
         }
           
        }


}
export default UserConsolePlugin;


