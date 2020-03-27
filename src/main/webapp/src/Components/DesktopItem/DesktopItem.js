import React,{Component} from 'react';
import $ from 'jquery';
import './DesktopItem.scss';
import folderImg from '../../images/Folder.png';
class DesktopItem extends Component{

    render() {


      var style={
        top:this.props.top,
        left:this.props.left
      };
        return (<div className={"desktop-item"}
          style={style}
          onDoubleClick={()=>this.props.onDoubleClick(this.props.item)}
        >

          <img id="icon-image" title="test" src={this.props.icon}
            className={"app/"+this.props.item.appType+"/"+this.props.item.appId+"/"+this.props.item.appName}
          />
          <div id="title">{this.props.item.appName}</div> 

        </div>)
      }


}
export default DesktopItem;
// <div id = {this.props.type} className={"desktop-item-"+this.props.type}></div>

