import React,{Component} from 'react';
import './FolderPlugin.scss';
import DesktopItem from '../../DesktopItem/DesktopItem';
class FolderPlugin extends Component{

    render() {

        // return (<div>
        //     {this.renderFolderItems()}
        // </div>);
        return <div className="desktop-item-view-folder">
          
        </div>
      }

      renderFolderItems(){
        var desktopItemList = [];
        var rowNo =1;
        var columnNo =1;
        var horizontalGridSize=90;
        var vertialGridSize=100;
        var folderContent = this.props.itemData['payload'];
         for(var item in folderContent){
          desktopItemList.push(
          <DesktopItem type={folderContent[item]}
          key={item} 
          name={item} 
          top={rowNo*vertialGridSize+'px'} 
          left={columnNo*horizontalGridSize+'px'}
          onDoubleClick={this.props.onDoubleClick.bind(this)}
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


