import React,{Component} from 'react';
import './TeamConsolePlugin.css';

class TeamConsolePlugin extends Component{


  componentDidMount(){
    //this.getPayload(this.props.item.appId);
  }
    render() {

        // return (<div>
        //     {this.renderFolderItems()}
        // </div>);
        if(this.state.loading){
            return (<div className='team-console'>
         Team app
            </div>);
        }

      }




}
export default TeamConsolePlugin;


