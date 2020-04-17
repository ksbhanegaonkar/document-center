import React,{Component} from 'react';
import './UserConsolePlugin.css';

class UserConsolePlugin extends Component{

    state={
        userName:"",
        email:"",
        successMsg:''
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
                     </div>);
        }

    
        setUserName(e){
            this.setState({userName:e.target.value});
        }

        setEmail(e){
            this.setState({email:e.target.value});
        }

        addUser(){
            this.setState({successMsg:"User added successfully...!"});
        }


}
export default UserConsolePlugin;


