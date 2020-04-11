import React,{Component} from 'react';
import './RenameScreen.scss';
class RenameScreen extends Component{

    state={
        newName:""
    };

    render() {
        var style={
          display:this.props.isRename?'block':'none',
        };
        const [app, type, id, name] = this.props.appToRename.split("/");
        return (<div className="rename-screen" style={style}>
            <div className="rename-pannel">
            <div>Renaming {name}
                <button onClick={() => this.copyCurrentName(name)}>Copy current name</button>
            </div> 
                <input type="text" value={this.state.newName} onChange={this.updateName.bind(this)}></input>
                <button onClick={this.rename.bind(this)}>Rename</button>
            </div>

        </div>)
      }


      componentDidMount(){

        }
    

      rename(){
            this.props.doneRename();
        }

        componentWillUpdate(){
            // const [app, type, id, name] = this.props.appToRename.split("/");
            // console.log("name is ::::  "+name);
            // //this.state.newName=name;
        }

        copyCurrentName(name){
            this.setState({newName:name});
        }

        updateName(e){
            this.setState({newName:e.target.value});
        }

}
export default RenameScreen;