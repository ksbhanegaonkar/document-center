import React,{Component} from 'react';
import $ from 'jquery';
import './MyContextMenu.scss';

class MyContextMenu extends Component {
    currentContextMenuOption = 'Desktop-wallpaper';
    state = {
        visible: false,
        contextMenuOption :['New Sprint','New User Story','Copy','Cut','Paste'],
        startButtonContextMenuOption :['Option 1','Option 2','Option 3','Option 4'],
        taskBarContextMenuOption :['Option 5','Option 6','Option 7','Option 8']
    };
    
    calculateTopLeftCredential(){

        var style = {
            position:'absolute',
            top:0,
            left:0
        };

        const clickX = this.props.xPosition;
        const clickY = this.props.yPosition;
        const screenW = window.innerWidth;
        const screenH = window.innerHeight;
        const rootW = 100;
        const rootH = 100;
        
        const right = (screenW - clickX) > rootW;
        const left = !right;
        const top = (screenH - clickY) > rootH;
        const bottom = !top;

        
        if (right) {
            style.left = `${clickX + 5}px`;
        }
        
        if (left) {
            style.left = `${clickX - rootW - 5}px`;
        }
        
        if (top) {
            style.top = `${clickY + 5}px`;
        }
        
        if (bottom) {
            style.top = `${clickY - rootH - 5}px`;
        }
        return style;


    }

    render(){
        if(this.props.visible){

            return(
                <div className="contextMenu" style={this.calculateTopLeftCredential()}>
                    {this.renderMenuItem()}
                </div>
            );
        }
        else return null;

    }

    renderMenuItem(){
        var contextMenuItems = [];

        for(var i=0;i<this.props.menuItemList.length;i++){
            contextMenuItems.push(<div key={this.props.menuItemList[i]} className="contextMenu--option"
            
            onClick={this.props.onContextMenuClick}
            
            >{this.props.menuItemList[i]}</div>);
        }
        return contextMenuItems;
    }
}

export default MyContextMenu;