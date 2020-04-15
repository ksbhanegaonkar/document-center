import React,{Component} from 'react';
import { ContextMenu, MenuItem, ContextMenuTrigger } from "react-contextmenu";
import $ from 'jquery';
import './Desktop.css';
import TaskBar from '../TaskBar/TaskBar';
import StartMenu from '../StartMenu/StartMenu';
import MyContextMenu from '../ContextMenu/MyContextMennu';
import DesktopItem from '../DesktopItem/DesktopItem';
import { visitLexicalEnvironment } from 'typescript';
import DesktopItemView from '../DesktopItemView/DesktopItemView';
import LoadingScreen from '../LodingScreen/LoadingScreen';
import { get } from 'https';
import { delay } from 'q';
import {withRouter} from 'react-router-dom';
import {postRequest,getRequest,downloadFilePostRequest,uploadFilePostRequest} from '../Utils/RestUtil';
import { conditionalExpression } from '@babel/types';
import RenameScreen from '../RenameScreen/RenameScreen';
import UpdateAppScreen from '../UpdateAppScreen/UpdateAppScreen';
class Desktop extends Component{
  
  constructor(props){
    super(props);
   

    this.state.contextMenuOption = {
      "desktop-wallpaper":['New Sprint','New User Story','Refresh','Copy','Cut','Paste'],
      "start-menu-button":['Option 1','Option 2','Option 3','Option 4'],
      "task-bar":['Option 5','Option 6','Option 7','Option 8'],
      "folder":['Open folder','Open folder in new window','Bookmark folder','Copy Folder','Rename Folder','Delete Folder'],
      "file":['Open file','Open file in new window','Bookmark file','Copy file','Rename File','Delete file']
    };
    this.state.startMenuOption =['My Folder','My Bookmarks','My Notes','Logout'];
    
    

  
  }

  state = { 
    contextMenuVisible: false,
    startMenuVisible:false,
    mouseXposition:0,
    mouseYposition:0,
    mouseButtonType:'',
    clickedComponentClass:'',
    rightClickedAppName:'',
    currentLiveAppId:0,
    
    iconsList:{},

    desktopItems:[],
    desktopItemViews:{},

    taskBarItems:{},

    dataloding:false,
    rename:false,
    updateApp:false,

    jwtToken:'',

    loggedInUserName:''

};

    componentDidMount() {
      document.addEventListener('dragend', this.onDragFinish.bind(this));
      document.addEventListener('contextmenu', this.handleContextMenu.bind(this));
      document.addEventListener('click', this.handleClick.bind(this));
      //document.addEventListener('drag', this.mouseUp.bind(this));
      //document.addEventListener('scroll', this._handleScroll);
      this.initDesktop();
     // this.loadDesktopIcons();
      this.loadDesktopItems();

    }

    componentWillUnmount() {
    document.removeEventListener('dragend', this.onDragFinish.bind(this));
    document.removeEventListener('contextmenu', this.handleContextMenu.bind(this));
    document.removeEventListener('click', this.handleClick.bind(this));
    //document.removeEventListener('drag', this.mouseUp.bind(this));
    //document.removeEventListener('scroll', this._handleScroll);
    }



    handleContextMenu(event){


      const componentClicked = event.target.className;
      const xPosition = event.clientX;
      const yPosition = event.clientY;

      event.preventDefault();
      if(componentClicked === 'start-menu-button' 
        ||componentClicked === 'task-bar'){

          
          this.setState(
              { mouseXposition:xPosition,
                mouseYposition:yPosition,
                clickedComponentClass:componentClicked,
                rightClickedAppName:componentClicked,
                mouseButtonType:'right-click',
                contextMenuVisible:true,
                startMenuVisible:false
              });

             // this.sendDesktopUpdate(event.target.innerText);
        }
        else if(componentClicked.includes("app/file")){
          this.setState(
            { mouseXposition:xPosition,
              mouseYposition:yPosition,
              clickedComponentClass:"file",
              mouseButtonType:'right-click',
              rightClickedAppName:componentClicked,
              contextMenuVisible:true,
              startMenuVisible:false
            });
        }
        else if(componentClicked.includes("app/folder")){
          this.setState(
            { mouseXposition:xPosition,
              mouseYposition:yPosition,
              clickedComponentClass:"folder",
              rightClickedAppName:componentClicked,
              mouseButtonType:'right-click',
              contextMenuVisible:true,
              startMenuVisible:false
            });
        }else if(componentClicked === 'desktop-wallpaper'
            ||componentClicked === 'desktop-item-view-folder' ) {
          
          this.setState(
            { mouseXposition:xPosition,
              mouseYposition:yPosition,
              clickedComponentClass:'desktop-wallpaper',
              rightClickedAppName:componentClicked,
              mouseButtonType:'right-click',
              contextMenuVisible:true,
              startMenuVisible:false
            });
        }

      }



    
      initDesktop(){ 
        this.setState({dataloding:true});
        postRequest('/onaction',{state:'init'},
        (data) =>{
          this.setState(data);
        });
        this.setState({dataloding:false});
      }

      // loadDesktopIcons(){
      //   this.setState({dataloding:true});
      //   postRequest('/onaction',{state:"update",action:"on-desktop-icons-load"},
      //   (data) =>{
      //     this.setState({iconsList:data});
      //   });
      // }
      loadDesktopItems(){
        this.setState({dataloding:true});
        getRequest('/getdesktopitems',
        (data) =>{
            this.setState({desktopItems:data,dataloding:false});         
        }
        );
      }


    handleClick(event){
      //event.preventDefault();
        const componentClicked = event.target.className;
        var isStartMenuVisible = false;
        if(componentClicked === 'start-menu-button'){
          isStartMenuVisible=true;
        }
        this.setState(
            { 
              contextMenuVisible:false,
              startMenuVisible:isStartMenuVisible
            });
            
            //this.sendDesktopUpdate(event.target.innerText);
            

    };


    onDragFinish(event) {
      if(event.target.className.includes('desktop-item')){
        /*This is used for grid alignment of icons
        event.target.style.left=`${event.clientX-event.clientX%70-25}px`;
        event.target.style.top=`${event.clientY-event.clientY%80-25}px`;*/

        event.target.style.left=`${event.clientX-30}px`;
        event.target.style.top=`${event.clientY-30}px`;

      }

    }
    
    mouseUp(event) {
    }

    doneRename(){
      this.setState({rename:false});
      let parentAppId = 0;
      for (var key in this.state.taskBarItems) {
        if (this.state.taskBarItems.hasOwnProperty(key)) {
          var val = this.state.taskBarItems[key];
          if(val==='block'){
            parentAppId = key;
          }
        }
      }

        this.loadDesktopItems()
        if(parentAppId != 0){
          let currentParentFolder = this.state.desktopItemViews[parentAppId];
          this.onDesktopItemViewClose(parentAppId);
          this.onDesktopIconDoubleClick(currentParentFolder);
        }


     
    }

    cancleAppUpdate(){
      this.setState({updateApp:false});
    }
    getParentAppId(){
      let parentAppId = 0;
      for (var key in this.state.taskBarItems) {
        if (this.state.taskBarItems.hasOwnProperty(key)) {
          var val = this.state.taskBarItems[key];
          if(val==='block'){
            parentAppId = key;
          }
        }
      }
      return parentAppId;
    }

    onContextMenuOptionClick(event){

      if(event.target.childNodes[0].data.includes("Download")){
        const [app, type, id, name] = this.state.rightClickedAppName.split("/");
        downloadFilePostRequest('/downloadapp',{appId:id,version:0,appName:name,option:"Download File"},
        (response) => {                
                  let filename = name;
                  //let disposition = response.headers.get('Content-Disposition');
                  //console.log("Disposition is ::: "+disposition);
                      // let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                      // let matches = filenameRegex.exec(disposition);
                      // if (matches != null && matches[1]) { 
                      //   filename = matches[1].replace(/['"]/g, '');
                      // }

        
        
                  response.blob().then(blob => {
                    let url = window.URL.createObjectURL(blob);
                    let a = document.createElement('a');
                    a.href = url;
                    a.download = filename;
                    a.click();
                    });
             

        }
        );

      }else if(event.target.childNodes[0].data.includes("Upload")){
        this.refs.fileUploader.click();
      }else if(event.target.childNodes[0].data.includes("Refresh")){
         
      }else if(event.target.childNodes[0].data.includes("Update")){
        this.setState({updateApp:true});
      }else if(event.target.childNodes[0].data.includes("History")){
        const [app, type, id, name] = this.state.rightClickedAppName.split("/");
        this.onDesktopIconDoubleClick({appId:id,appName:name+"_history",appType:"history",fileName:name});
      }else if(event.target.childNodes[0].data.includes("Rename")){
        this.setState({rename:true});
      }else{
        let parentAppId = 0;
        for (var key in this.state.taskBarItems) {
          if (this.state.taskBarItems.hasOwnProperty(key)) {
            var val = this.state.taskBarItems[key];
            if(val==='block'){
              parentAppId = key;
            }
          }
        }
        postRequest('/oncontextmenuaction',{item:this.state.rightClickedAppName,option:event.target.childNodes[0].data,parentAppId:parentAppId},
        (data) => {
          this.loadDesktopItems()
          if(parentAppId != 0){
            let currentParentFolder = this.state.desktopItemViews[parentAppId];
            this.onDesktopItemViewClose(parentAppId);
            this.onDesktopIconDoubleClick(currentParentFolder);
          }
        }
        );


      }

    }

    renderDesktopItems(){
      if(this.getParentAppId()===0){
        let desktopItemList = [];
        let rowNo =0.7;
        let columnNo =1;
        let horizontalGridSize=100;
        let vertialGridSize=120;
         for(let i=0;i<this.state.desktopItems.length;i++){
           let item = this.state.desktopItems[i];
           let type = item.appType;
          desktopItemList.push(<DesktopItem
          icon={this.state.iconsList[type]}  
          key={item.appId} item={item} top={rowNo*vertialGridSize+'px'} left={columnNo*horizontalGridSize+'px'}
          onDoubleClick={this.onDesktopIconDoubleClick.bind(this)}
          ></DesktopItem>);
          // rowNo++;
          // if(rowNo >5){
          //   rowNo=1;
          //   columnNo++;
          // }
  
          columnNo++;
          if(columnNo >12){
            columnNo=1;
            rowNo++;
          }
          
         }
  
         return desktopItemList;
      }

    }


    onDesktopIconDoubleClick(item){
           this.setState({dataloding:true});
          // console.log("name is ::::"+name);
          // postRequest('/onaction',{state:"update",action:"on-double-click",desktopItem:name},
          // (data) =>{
          //   var newTaskBarItems = this.state.taskBarItems;
          //     for(var i in newTaskBarItems){
          //       if(newTaskBarItems[i] == 'block'){
          //         newTaskBarItems[i]='none';
          //       }
          //     }
          //     var itemName = data['desktop-item-data'].name;
          //     newTaskBarItems[itemName] = 'block';
          //     var newDesktopItemViews = this.state.desktopItemViews;
          //     newDesktopItemViews[itemName]=data['desktop-item-data'];
          //     this.setState({desktopItemViews:newDesktopItemViews,taskBarItems:newTaskBarItems,dataloding:false});         
          // }
          // );
          if(this.state.taskBarItems[item.appId]===undefined){
            var newTaskBarItems = this.state.taskBarItems;
            for(let key in newTaskBarItems){
              newTaskBarItems[key] = 'none';
            }
            newTaskBarItems[item.appId] = 'block';
            var newDesktopItemViews = this.state.desktopItemViews;
            newDesktopItemViews[item.appId] = item;
            this.setState({desktopItemViews:newDesktopItemViews,taskBarItems:newTaskBarItems,dataloding:false});
          }else{
            var newTaskBarItems = this.state.taskBarItems;
            for(let key in newTaskBarItems){
              newTaskBarItems[key] = 'none';
            }
            newTaskBarItems[item.appId] = 'block';
            this.setState({taskBarItems:newTaskBarItems,dataloding:false});
          }

    }

    onDesktopItemViewClose(appId){
      var newTaskBarItems = this.state.taskBarItems;
      var newDesktopItemViews = this.state.desktopItemViews;
      delete newTaskBarItems[appId];
      delete newDesktopItemViews[appId]
      let previousActiveAppId = 0;
      for(let key in newTaskBarItems){
        previousActiveAppId = key;
      }
      if(previousActiveAppId != 0)
      newTaskBarItems[previousActiveAppId] = 'block';
      this.setState({desktopItemViews:newDesktopItemViews,taskBarItems:newTaskBarItems});
    }

    onDesktopItemViewMinimize(appId){
      var newTaskBarItems = this.state.taskBarItems;
      newTaskBarItems[appId] = 'none';
      this.setState({taskBarItems:newTaskBarItems});    
    }


    onDesktopItemViewActive(appId){
      var newTaskBarItems = this.state.taskBarItems;
        for(var i in newTaskBarItems){
          if(newTaskBarItems[i] == 'block'){
            newTaskBarItems[i]='none';
          }
        }
        newTaskBarItems[appId] = 'block';
        this.setState({taskBarItems:newTaskBarItems});  
    }

    onDesktopItemViewInFocus(name){
      var newTaskBarItems = this.state.taskBarItems;
        for(var i in newTaskBarItems){
          if(newTaskBarItems[i] == 'inFocus'){
            newTaskBarItems[i]='block';
          }
        }
        newTaskBarItems[name] = 'inFocus';
        this.setState({taskBarItems:newTaskBarItems});  
    }

    onTaskBarItemClick(appId){
      
      if(this.state.taskBarItems[appId]==='block'){
        this.onDesktopItemViewMinimize(appId);
      }
      else {
        this.onDesktopItemViewActive(appId);
      }

    }

    onStartMenuItemClick(event){
      let clickedButton = event.target.childNodes[0].data;
      if(clickedButton==='Logout'){
        localStorage.removeItem("jwtToken");
        this.props.history.push("/");
        
      }else if(clickedButton==='My Folder'){
        console.log("My folder clidked ::: ");
        getRequest("/getpersonalapps",(data) =>{
          console.dir(data);
          this.onDesktopIconDoubleClick(data);
        });
      }else if(clickedButton==='My Notes'){
        console.log("My Notes clidked");
        
      }else if(clickedButton==='My Bookmarks'){
        console.log("My Bookmarks clidked");
        
      }
    }

    renderDesktopItemView(){
      var desktopItemViewList = [];
      //  for(var item in this.state.desktopItemViews){
      //   desktopItemViewList.push(<DesktopItemView
      //     key={item} name={item} 
      //     onClose={this.onDesktopItemViewClose.bind(this)}
      //     onMinimize={this.onDesktopItemViewMinimize.bind(this)}
      //     activeStatus={this.state.taskBarItems[item]}
      //     onDoubleClick={this.onDesktopIconDoubleClick.bind(this)}
      //     desktopItemViewData={this.state.desktopItemViews[item]}
      //   ></DesktopItemView>);
      //  }
        for(let appId in this.state.desktopItemViews){
         let item = this.state.desktopItemViews[appId];
          desktopItemViewList.push(<DesktopItemView
          key={item.appId} item={item} 
          onClose={this.onDesktopItemViewClose.bind(this)}
          onMinimize={this.onDesktopItemViewMinimize.bind(this)}
          activeStatus={this.state.taskBarItems[item.appId]}
          onDoubleClick={this.onDesktopIconDoubleClick.bind(this)} 
          iconsList={this.state.iconsList}
        ></DesktopItemView>);
       }

      return desktopItemViewList;
    }

     readUploadedFileAsText (inputFile) {
      const temporaryFileReader = new FileReader();
  
      return new Promise((resolve, reject) => {
        temporaryFileReader.onerror = () => {
          temporaryFileReader.abort();
          reject(new DOMException("Problem parsing input file."));
        };
  
        temporaryFileReader.onload = () => {
          resolve(temporaryFileReader.result);
        };
        temporaryFileReader.readAsText(inputFile);
      });
    };
  
    uploadFile = async e => {
      e.preventDefault();
      let parentAppId = 0;
      for (var key in this.state.taskBarItems) {
        if (this.state.taskBarItems.hasOwnProperty(key)) {
          var val = this.state.taskBarItems[key];
          if(val==='block'){
            parentAppId = key;
          }
        }
      }

      const formData = new FormData();
      formData.append('file', e.target.files[0]);
      formData.append('parentAppId', parentAppId);
      uploadFilePostRequest("/upload",formData,(data)=>{
        this.loadDesktopItems()
        if(parentAppId != 0){
          let currentParentFolder = this.state.desktopItemViews[parentAppId];
          this.onDesktopItemViewClose(parentAppId);
          this.onDesktopIconDoubleClick(currentParentFolder);
        }
      });
    }

    uploadMultipleFile = async e => {
      e.preventDefault();
      this.setState({dataloding:true});
      let parentAppId = 0;
      for (var key in this.state.taskBarItems) {
        if (this.state.taskBarItems.hasOwnProperty(key)) {
          var val = this.state.taskBarItems[key];
          if(val==='block'){
            parentAppId = key;
          }
        }
      }

      const formData = new FormData();
      
      console.dir(e.target);
      let fileListArray = Array.from(e.target.files);
      let len = fileListArray.length;
      for(let i=0;i<len;i++){
        formData.append('files',fileListArray[i]);
      }  
      formData.append('parentAppId', parentAppId);
      uploadFilePostRequest("/uploadmultiple",formData,(data)=>{
        this.loadDesktopItems()
        if(parentAppId != 0){
          let currentParentFolder = this.state.desktopItemViews[parentAppId];
          this.onDesktopItemViewClose(parentAppId);
          this.onDesktopIconDoubleClick(currentParentFolder);
        }
        this.refs.fileUploader.value=null;
      });
    }

    render() {
        return (<div 
        className="desktop-wallpaper">
        <LoadingScreen isLoading={this.state.dataloding}></LoadingScreen>
        <RenameScreen isRename={this.state.rename} 
        appToRename={this.state.rightClickedAppName}
        doneRename={this.doneRename.bind(this)} 
        parentAppId={this.getParentAppId()}
        ></RenameScreen>
        <UpdateAppScreen isAppUpdate={this.state.updateApp} 
        appToUpdate={this.state.rightClickedAppName}
        cancleAppUpdate={this.cancleAppUpdate.bind(this)} 
        parentAppId={this.getParentAppId()}
        ></UpdateAppScreen>
        <MyContextMenu visible={this.state.contextMenuVisible} 
              xPosition={this.state.mouseXposition}
              yPosition={this.state.mouseYposition} 
              menuItemList={this.state.contextMenuOption[this.state.clickedComponentClass]}
              onContextMenuClick={this.onContextMenuOptionClick.bind(this)}>
          </MyContextMenu>
         <TaskBar taskBarItems={this.state.taskBarItems}
         desktopItemViews={this.state.desktopItemViews}
         onItemClick={this.onTaskBarItemClick.bind(this)}
         ></TaskBar>
          <StartMenu visible={this.state.startMenuVisible}
          menuItemList={this.state.startMenuOption}
          loggedUserName={this.state.loggedInUserName}
          onStartMenuItemClick={this.onStartMenuItemClick.bind(this)}>
         </StartMenu> 
         {this.renderDesktopItems()}
         {this.renderDesktopItemView()}
         <input type="file"  id="FileUpload" ref="fileUploader" style={{display: "none"}} onChange={this.uploadMultipleFile.bind(this)} multiple/>
        </div>)
      }


}
export default withRouter(Desktop);