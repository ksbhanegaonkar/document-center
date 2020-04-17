import React,{Component} from 'react';
import './IconConsolePlugin.css';

class IconConsolePlugin extends Component{


  componentDidMount(){
    //this.getPayload(this.props.item.appId);
  }
    render() {

        // return (<div>
        //     {this.renderFolderItems()}
        // </div>);
        if(this.state.loading){
            return (<div className='icon-console'>
         Icon app
            </div>);
        }

      }




}
export default IconConsolePlugin;


