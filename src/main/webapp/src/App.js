import React,{Component} from 'react';
import './App.css';
import Desktop from './Components/Desktop/Desktop';
import LoginForm from './Components/LoginForm/LoginForm';
import {BrowserRouter as Router,NavLink,Redirect,Route,Switch} from 'react-router-dom';
import AuthenticatedComponent from './Components/AuthenticatedComponent/AuthenticatedComponent';

class App extends Component {
  state={
    userStatus:false
  }
  render(){
  localStorage.removeItem("jwtToken");
  return (
    <Router>
        <Switch>
          <Route path="/" exact strict component={LoginForm}></Route>
          <AuthenticatedComponent>
          <Route path="/desktop" exact strict component={Desktop}></Route>
          </AuthenticatedComponent>
        </Switch>
    </Router>
  );
  }


}

export default App;
