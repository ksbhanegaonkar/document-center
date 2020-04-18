import React,{Component} from 'react';
import './DesktopItemView.scss';
import FolderPlugin from '../DesktopItemViewPlugins/FolderPlugin/FolderPlugin';
import FilePlugin from '../DesktopItemViewPlugins/FilePlugin/FilePlugin';
import {getRequest,postRequest} from '../Utils/RestUtil';
import HistoryPlugin from '../DesktopItemViewPlugins/HistoryPlugin/HistoryPlugin';
import AdminPlugin from '../DesktopItemViewPlugins/AdminPlugin/AdminPlugin';
import UserConsolePlugin from '../DesktopItemViewPlugins/UserConsolePlugin/UserConsolePlugin';
import TeamConsolePlugin from '../DesktopItemViewPlugins/TeamConsolePlugin/TeamConsolePlugin';
import IconConsolePlugin from '../DesktopItemViewPlugins/IconConsolePlugin/IconConsolePlugin';
import TeamManagerPlugin from '../DesktopItemViewPlugins/TeamManagerPlugin/TeamManagerPlugin';
class DesktopItemView extends Component{

    state={
      payload:""
    };

    componentDidMount(){
      //this.getPayload(this.props.item.appId);
    }

    render() {
      var status;
      var zIndex;
      if(this.props.activeStatus === 'inFocus'){
        status = 'block';
        zIndex=1;
      }else if(this.props.activeStatus === 'block'){
        status = 'block';
        zIndex=0;
      }else{
        status = 'none';
        zIndex=0;
      }
      var style={
        "display":status,
        "zIndex":zIndex
      };
        return (<div className={"desktop-item-view"} style={style}>
            { //<div id = {this.props.type} className={"desktop-item-"+this.props.type}></div>
          /* <div id="title" className={"desktop-item-"+this.props.type}>{this.props.name}</div> */}
           <div className="top-bar">
           {this.props.item.appName}
           <div className="top-bar-button-pallet">
                    <button className="minimize-it"
                    onClick={()=>this.props.onMinimize(this.props.item.appId)}>-</button>
                    <button className="top-bar-button-pallet-close-it"
                    onClick={()=>this.props.onClose(this.props.item.appId)}
                    >X</button>
            </div>
           </div>

            {this.renderDesktopItemViewPlugin()}
        </div>)
      }

      renderDesktopItemViewPlugin(){
        if(this.props.item.appType==='folder'  || this.props.item.appType==='folder-personal'){
          return(
            <div className="desktop-item-view-folder">
              <FolderPlugin 
              item={this.props.item}
              onDoubleClick={this.props.onDoubleClick}
              //getPayload={this.getPayload.bind(this)}
             // updatePayload={this.updatePayload.bind(this)} 
              iconsList={this.props.iconsList}
              ></FolderPlugin>
            </div>
          );
        }else if(this.props.item.appType==='file'){
            return(<div className="desktop-item-view-file">
              <FilePlugin item={this.props.item}
              updatePayload={this.updatePayload.bind(this)}
              ></FilePlugin>
            </div>
            );
        }else if(this.props.item.appType === 'history'){
              return(<div className="desktop-item-view-history">
              <HistoryPlugin item={this.props.item}
              ></HistoryPlugin>
            </div>
            );
        }else if(this.props.item.appType === 'admin'){
          return(<div className="desktop-item-view-admin">
          <AdminPlugin item={this.props.item}
          ></AdminPlugin>
        </div>
        );
        }else if(this.props.item.appType === 'user-console'){
          return(<div className="desktop-item-view-user-console">
          <UserConsolePlugin item={this.props.item}
          ></UserConsolePlugin>
        </div>
        );
        }else if(this.props.item.appType === 'team-console'){
          return(<div className="desktop-item-view-team-console">
          <TeamConsolePlugin item={this.props.item}
          ></TeamConsolePlugin>
        </div>
        );
        }else if(this.props.item.appType === 'icon-console'){
          return(<div className="desktop-item-view-icon-console">
          <IconConsolePlugin item={this.props.item}
          ></IconConsolePlugin>
        </div>
        );
        }else if(this.props.item.appType === 'team-manager'){
          return(<div className="desktop-item-view-team-manager">
          <TeamManagerPlugin item={this.props.item}
          ></TeamManagerPlugin>
        </div>
        );
        }

      }

      getPayload(appId){
        getRequest('/getapppayload/'+appId,(data)=>{
          console.log("payload is ::::::"+data.payload);
          this.setState({payload:data.payload});
        });
        
      }

      updatePayload(appId,payload){
          postRequest('/updateapppayload',{"appId":appId,"payload":payload},
          (data)=>{
            this.setState({payload:data.payload});
          }
          )
      }

}
export default DesktopItemView;


