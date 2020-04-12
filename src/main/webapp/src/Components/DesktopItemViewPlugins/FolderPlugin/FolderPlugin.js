import React,{Component} from 'react';
import './FolderPlugin.scss';
import DesktopItem from '../../DesktopItem/DesktopItem';
import {getRequest,postRequest} from '../../Utils/RestUtil';
class FolderPlugin extends Component{

  state = {
    desktopItems : [],
    loading:true
  };

  componentDidMount(){
    this.getPayload(this.props.item.appId);
  }
    render() {

        // return (<div>
        //     {this.renderFolderItems()}
        // </div>);
        if(this.state.loading){
            return (<div className='loading-message'>
            <span>Loading items...</span>
     </div>);
        }else{
            return <div className="desktop-item-view-folder">
            {this.renderFolderItems()}
            </div>
        }

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
          onDoubleClick={() => this.props.onDoubleClick(item)}
          ></DesktopItem>);
          rowNo++;
          if(rowNo >5){
            rowNo=1;
            columnNo++;
          }
          
         
        }
        return desktopItemList;
      }
      
      getPayload(appId){
        getRequest('/getapppayload/'+appId,(data)=>{
          console.log("payload is for folder ::::::"+data.payload);
          this.setState({desktopItems:JSON.parse(data.payload),loading:false});
        });
        
      }

}
export default FolderPlugin;


