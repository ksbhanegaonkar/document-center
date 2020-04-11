import React,{Component} from 'react';
import './RenameScreen.scss';
class RenameScreen extends Component{

    render() {
        var style={
          display:this.props.isRename?'block':'none'
        };
        return (<div className="rename-screen" style={style}>
          <p id="rename-text">Renaming {this.props.appToRename} ....</p>

        </div>)
      }


}
export default RenameScreen;