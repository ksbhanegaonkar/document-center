import React,{Component} from 'react';
import $ from 'jquery';
import './TaskBar.scss';
import StartMenu from '../StartMenu/StartMenu';
import TaskBarItem from './TaskBarItem';
class TaskBar extends Component{

    render() {

        return (<div className="task-bar">
           {this.renderTaskBarItems()}

        </div>)
      }


      renderTaskBarItems(){
     

        var taskBarItemsList = [];

        for(var item in this.props.taskBarItems){

        taskBarItemsList.push(
          <TaskBarItem key={item} item={this.props.desktopItemViews[item]} activeStatus={this.props.taskBarItems[item]}
          onItemClick={this.props.onItemClick}>
          </TaskBarItem>);
        }
       return taskBarItemsList;
      }

}
export default TaskBar;