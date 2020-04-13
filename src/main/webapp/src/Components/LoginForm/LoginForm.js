import React, { Component } from "react";
import { Button, FormGroup, FormControl, FormLabel } from "react-bootstrap";
import {withRouter} from 'react-router-dom';
import "./LoginForm.css";
import {authPostRequest} from '../Utils/RestUtil';


class LoginForm extends Component{
  state={
    userName:'',
    pass:'',
    errorMsg:'',
    loadingMsg:'',
    loading:false
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

  handleSubmit(event) {
      
    event.preventDefault();
    this.setState({loading:true,loadingMsg:"Logging in..."});
    authPostRequest({username:this.state.userName,password:this.state.pass},
      (data) =>{
             if(data.token === undefined){
                this.setState({errorMsg:'Invalid user credential !!!'});
                localStorage.removeItem("jwtToken");
                console.log('user is not valid...');
                this.props.history.push("/");
              }else{
                localStorage.setItem("jwtToken","Bearer "+data.token);
                console.log('redirecting to destkop');
                this.props.history.push("/desktop");
              }
              this.setState({loading:false,loadingMsg:""});
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
  render(){
    if(!this.state.loading){
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
    }else{
      return(<div className='logging-in-message'>
                 <span>{this.state.loadingMsg}</span>
             </div>);
    }
    
  }

  
}
export default withRouter(LoginForm);
