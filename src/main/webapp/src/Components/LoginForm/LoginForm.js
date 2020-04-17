import React, { Component } from "react";
import { Button, FormGroup, FormControl, FormLabel } from "react-bootstrap";
import {withRouter} from 'react-router-dom';
import "./LoginForm.css";
import {authPostRequest,passwordResetPostRequest} from '../Utils/RestUtil';


class LoginForm extends Component{
  state={
    userName:'',
    pass:'',
    errorMsg:'',
    loadingMsg:'',
    loading:false,
    passwordReset:false,
    newPassword:'',
    newPasswordConfirm:''
  }
  constructor(props){
    super(props);
    
  }

  validateForm() {
    return this.state.userName.length > 0 && this.state.pass.length > 0;
  }

  setUsername(name){
    this.setState({userName:name});
  }
  setPassword(pass){
    this.setState({pass:pass});
  }

  setNewPassword(pass){
    this.setState({newPassword:pass});
  }

  setNewConfirmPassword(pass){
    this.setState({newPasswordConfirm:pass});
  }

  handleSubmit(event) {
      
    event.preventDefault();
    this.setState({loading:true,loadingMsg:"Logging in..."});
    authPostRequest({username:this.state.userName,password:this.state.pass},
      (data) =>{
             if(data.status === 401){
               console.log("Login data object is :::::"+data.status);
               console.dir(data);

               if(data.trace.includes("User credentials have expired")){
                this.setState({loading:false,loadingMsg:"",passwordReset:true,errorMsg:"Your password is expired, please reset password...!"});
               }else{
                this.setState({errorMsg:'Invalid user credential !!!'});
                localStorage.removeItem("jwtToken");
                console.log('user is not valid...');
                this.props.history.push("/");
               }

              }else{
                localStorage.setItem("jwtToken","Bearer "+data.token);
                console.log('redirecting to destkop');
                this.props.history.push("/desktop");
                this.setState({loading:false,loadingMsg:""});
              }
             
      }
      );

    // let headers = new Headers();
    // headers.set( 'Content-Type', 'application/json');
    // headers.set('Access-Control-Allow-Origin',"*");
    // fetch(new Request("http://localhost:8083/authenticate"),
    //   {
    //     headers:headers,
    //      method: 'POST', // or 'PUT'
    //      //mode:"no-cors",
    //      body: JSON.stringify({username:this.state.userName,password:this.state.pass}) // data can be `string` or {object}!

    //   }
    //      )
    // .then((res)=>res.json())
    // .then(data=>{ 
    //   console.log('Token is :::: '+data.token);
    //   if(data.token === undefined){
    //     this.setState({errorMsg:'Invalid user credential !!!'});
    //     localStorage.removeItem("jwtToken");
    //     console.log('user is not valid...');
    //     this.props.history.push("/");
    //   }else{
    //     localStorage.setItem("jwtToken","Bearer "+data.token);
    //     console.log('redirecting to destkop');
    //     this.props.history.push("/desktop");
    //   }
    // }).catch(err =>{
    //   localStorage.removeItem("jwtToken");

    //   console.log('user is not valid...');
    //   this.props.history.push("/");
    // });
  }


  handlePasswordResetSubmit(event) {
      
    event.preventDefault();
    if(this.state.newPassword !== this.state.newPasswordConfirm){
        this.setState({errorMsg:"New password is not matching with confirmed password...!"})
    }else{
    this.setState({loading:true,loadingMsg:"Resetting password..."});
    let formData = new FormData();
    formData.append("username","test");
    formData.append("oldPassword",this.state.pass);
    formData.append("newPassword",this.state.newPassword);
    passwordResetPostRequest(formData,
      (data) =>{
             if(data.status === 401){
               console.log("Login data object is :::::"+data.status);
               console.dir(data);

               if(data.trace.includes("User credentials have expired")){
                 console.log("Reset password logic here");
               }else{
                this.setState({errorMsg:'Invalid user credential !!!'});
                localStorage.removeItem("jwtToken");
                console.log('user is not valid...');
                this.props.history.push("/");
               }
               this.setState({loading:false,loadingMsg:"",errorMsg:"Failed to reset password...!"});
              }else{
                this.setState({loading:false,loadingMsg:"",errorMsg:"Password reseted successfully...!"});
              }
             
      }
      );
    }
    }

  render(){
    if(!this.state.loading && !this.state.passwordReset){
      return (
        <div className="Login">
          <form onSubmit={this.handleSubmit.bind(this)}>
            <FormGroup controlId="email" >
              <FormLabel>Username</FormLabel>
              <FormControl
                autoFocus
                type="text"
               // value={this.state.userName == null?this.state.userName:''}
                onChange={e => this.setUsername(e.target.value)}
              />
            </FormGroup>
            <FormGroup controlId="password" >
              <FormLabel>Password</FormLabel>
              <FormControl
                //value={this.state.pass?this.state.pass:''}
                onChange={e => this.setPassword(e.target.value)}
                type="password"
              />
            </FormGroup>
            <Button block 
            //disabled={!this.validateForm()} 
            type="submit">
              Login
            </Button>
            <div className='error-message'>
            <span>{this.state.errorMsg}</span>
            </div>
  

  
          </form>
  
        </div>
      );
    }else if(!this.state.loading && this.state.passwordReset){

      return (
        <div className="Login">
          <form onSubmit={this.handlePasswordResetSubmit.bind(this)}>
            <FormGroup controlId="email" >
              <FormLabel>Username</FormLabel>
              <FormControl
                autoFocus
                type="text"
               // value={this.state.userName == null?this.state.userName:''}
                onChange={e => this.setUsername(e.target.value)}
              />
            </FormGroup>
            <FormGroup controlId="password" >
              <FormLabel>Current Password</FormLabel>
              <FormControl
                //value={this.state.pass?this.state.pass:''}
                onChange={e => this.setPassword(e.target.value)}
                type="password"
              />
            </FormGroup>
            <FormGroup controlId="password" >
              <FormLabel>New Password</FormLabel>
              <FormControl
                //value={this.state.pass?this.state.pass:''}
                onChange={e => this.setNewPassword(e.target.value)}
                type="password"
              />
            </FormGroup>
            <FormGroup controlId="password" >
              <FormLabel>Verify Password</FormLabel>
              <FormControl
                //value={this.state.pass?this.state.pass:''}
                onChange={e => this.setNewConfirmPassword(e.target.value)}
                type="password"
              />
            </FormGroup>
            <Button block 
            //disabled={!this.validateForm()} 
            type="submit">
              Reset Password
            </Button>
            <div className='error-message'>
            <span>{this.state.errorMsg}</span>
            </div>
  

  
          </form>
  
        </div>
      );


    }
    else{
      return(<div className='logging-in-message'>
                 <span>{this.state.loadingMsg}</span>
             </div>);
    }
    
  }

  
}
export default withRouter(LoginForm);
