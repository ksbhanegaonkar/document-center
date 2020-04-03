import React,{Component} from 'react';
import './FolderPlugin.scss';
import DesktopItem from '../../DesktopItem/DesktopItem';
class FolderPlugin extends Component{

  state = {
    desktopItems : [{"appId":41,"appName":"New Folder","appType":"folder"}]
  };

    render() {

        // return (<div>
        //     {this.renderFolderItems()}
        // </div>);
        return <div className="desktop-item-view-folder">
          {this.renderFolderItems()}
        </div>
      }


      renderFolderItems(){
        let desktopItemList = [];
        let rowNo =1;
        let columnNo =1;
        let horizontalGridSize=90;
        let vertialGridSize=100;
         for(let i=0;i<this.state.desktopItems.length;i++){
           let item = this.state.desktopItems[i];
           let type = item.appType;
          desktopItemList.push(<DesktopItem
          icon={this.props.iconsList[type]}  
          key={item.appId} item={item} top={rowNo*vertialGridSize+'px'} left={columnNo*horizontalGridSize+'px'}
          //onDoubleClick={this.onDesktopIconDoubleClick.bind(this)}
          ></DesktopItem>);
          rowNo++;
          if(rowNo >5){
            rowNo=1;
            columnNo++;
          }
          
         
        }
        return desktopItemList;
      }
      


}
export default FolderPlugin;


