import React,{Component} from 'react';
import { getJWT } from '../Utils/JwtUtils';
import {withRouter} from 'react-router-dom';

class AuthenticatedComponent extends Component{
    constructor(props){
        super(props);
        this.state={
            userLoggedIn:true
        }
    }

    componentDidMount(){
        const jwt = localStorage.getItem("jwtToken");
        if(!jwt){
            this.props.history.push('/');
        }
        this.setState({userLoggedIn:true});
    }
    render(){
        if(this.state.userLoggedIn === true){

            return(
                <div>
                {this.props.children}
                </div>
            );
        }
        else{
            return(<h1>Loading...</h1>);

        }

    }

}

export default withRouter(AuthenticatedComponent);