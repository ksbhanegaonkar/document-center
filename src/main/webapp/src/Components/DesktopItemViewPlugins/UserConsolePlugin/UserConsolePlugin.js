import React,{Component} from 'react';
import './UserConsolePlugin.css';
import {getRequest,postRequest} from '../../Utils/RestUtil';

class UserConsolePlugin extends Component{

    state={
        userName:"",
        email:"",
        successMsg:'',
        errorMsg:'',
        allUsers:[],
        loadingMessage:""
    }
  componentDidMount(){
    this.setState({loadingMessage:"Fetching all users..."});
    this.fetchAllUsers();
  }
    render() {


            return (<div className='user-console'>
                        <label className="user-console-label">User Name :</label>
                        <input className="user-console-input" disabled={this.state.loadingMessage} type="text" onChange={this.setUserName.bind(this)} placeholder="User Name" name="username" required/>
                        <br></br>
                        <label className="user-console-label">Email :</label>
                        <input className="user-console-input" disabled={this.state.loadingMessage} type="text" onChange={this.setEmail.bind(this)} placeholder="Enter Email" name="email" required/>
                        <br></br>
                        <button onClick={this.addUser.bind(this)} disabled={this.state.loadingMessage}>Add User</button>
                        <br></br>

                         <div className="display-all-users">

                            <label className="user-console-label">All User List :</label>
                            <br></br>
                                <select id="all-users" disabled={this.state.loadingMessage} name="all-users" multiple>
                                {this.state.allUsers.map(u=>{
                                    return (<option key={u} value={u}>{u}</option>);
                                })}
                                </select>

                            </div>
                                
                        <div className="user-added-success-message">
                            <span>{this.state.successMsg}</span>
                        </div>
                         <div className="user-added-error-message">
                            <span>{this.state.errorMsg}</span>
                        </div>
                        <div className="user-added-loading-message">
                            <span>{this.state.loadingMessage}</span>
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
                this.setState({errorMsg:"Please add user name...!",successMsg:""});
            }else if(this.state.email===""){
                this.setState({errorMsg:"Please add email name...!",successMsg:""});
            }if(this.state.allUsers.includes(this.state.userName)){
                this.setState({errorMsg:"User Already exists...!",successMsg:""});
            }else{
            this.setState({loadingMessage:"Adding user...",successMsg:"",errorMsg:""});
            postRequest("/adduser",{username:this.state.userName,email:this.state.email},
            (data)=>{  
                this.fetchAllUsers();
                this.setState({successMsg:"User added successfully...!",errorMsg:""});
            });
         }
           
        }

        fetchAllUsers(){
            getRequest("/getallusers",(data)=>this.setState({allUsers:data,loadingMessage:""}));
          }

}
export default UserConsolePlugin;


