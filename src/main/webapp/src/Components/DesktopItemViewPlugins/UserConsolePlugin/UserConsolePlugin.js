import React,{Component} from 'react';
import './UserConsolePlugin.css';

class UserConsolePlugin extends Component{


  componentDidMount(){
    //this.getPayload(this.props.item.appId);
  }
    render() {

        // return (<div>
        //     {this.renderFolderItems()}
        // </div>);
        if(this.state.loading){
            return (<div className='user-console'>
         User app
            </div>);
        }

      }




}
export default UserConsolePlugin;


