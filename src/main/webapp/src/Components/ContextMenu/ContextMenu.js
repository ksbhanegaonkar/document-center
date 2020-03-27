import React,{Component} from 'react';
import $ from 'jquery';
import './ContextMenu.scss';

class ContextMenu extends Component {
    currentContextMenuOption;
    state = {
        visible: false,
        contextMenuOption :['New Sprint','New User Story','Copy','Cut','Paste'],
        startButtonContextMenuOption :['Option 1','Option 2','Option 3','Option 4'],
        taskBarContextMenuOption :['Option 5','Option 6','Option 7','Option 8']
    };
    
    componentDidMount() {
     //   document.addEventListener('contextmenu', this._handleContextMenu);
     //   document.addEventListener('click', this._handleClick);
     //   document.addEventListener('scroll', this._handleScroll);
    };

    componentWillUnmount() {
     // document.removeEventListener('contextmenu', this._handleContextMenu);
     // document.removeEventListener('click', this._handleClick);
     // document.removeEventListener('scroll', this._handleScroll);
    }
    
    _handleContextMenu = (event) => {
        event.preventDefault();
       
        this.currentContextMenuOption = event.target.className;
        this.setState({ visible: true });
        const clickX = event.clientX;
        const clickY = event.clientY;
        const screenW = window.innerWidth;
        const screenH = window.innerHeight;
        const rootW = this.root.offsetWidth;
        const rootH = this.root.offsetHeight;
        
        const right = (screenW - clickX) > rootW;
        const left = !right;
        const top = (screenH - clickY) > rootH;
        const bottom = !top;
        
        if (right) {
            this.root.style.left = `${clickX + 5}px`;
        }
        
        if (left) {
            this.root.style.left = `${clickX - rootW - 5}px`;
        }
        
        if (top) {
            this.root.style.top = `${clickY + 5}px`;
        }
        
        if (bottom) {
            this.root.style.top = `${clickY - rootH - 5}px`;
        }
        this.root.style.zIndex='1';

    };

    _handleClick = (event) => {
        const { visible } = this.state;
        const wasOutside = !(event.target.contains === this.root);
        
        if (wasOutside && visible) this.setState({ visible: false, });
    };

    _handleScroll = () => {
        const { visible } = this.state;
        
        if (visible) this.setState({ visible: false, });
    };
    
    render() {
        const { visible } = this.state;
        
        return(visible || null) && 
            <div ref={ref => {this.root = ref}} className="contextMenu">
                {this.renderMenuItem()}
            </div>
    };

    shareThis(){
        console.log("Share this");
    }

    renderMenuItem(){
        console.log('Iterating menu item...');
        var contextMenuItems = [];
        var contextMenuOptionList;

        if(this.currentContextMenuOption === 'Desktop-wallpaper'){
            contextMenuOptionList = this.state.contextMenuOption;
        }
        else if(this.currentContextMenuOption === 'start-menu-button'){
            contextMenuOptionList = this.state.startButtonContextMenuOption;    
        }
        else if(this.currentContextMenuOption === 'Task-bar'){
            contextMenuOptionList = this.state.taskBarContextMenuOption;
        }
        for(var i=0;i<contextMenuOptionList.length;i++){
            contextMenuItems.push(<div key={contextMenuOptionList[i]} className="contextMenu--option">{contextMenuOptionList[i]}</div>);
        }
        return contextMenuItems;
    }
}

export default ContextMenu;