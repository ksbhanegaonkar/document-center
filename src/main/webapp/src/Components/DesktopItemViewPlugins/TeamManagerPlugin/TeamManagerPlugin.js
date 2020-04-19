import React,{Component} from 'react';
import './TeamManagerPlugin.css';
import {getRequest,postRequest} from '../../Utils/RestUtil';

class TeamManagerPlugin extends Component{

    state={
      teamDL:"",
        successMsg:'',
        errorMsg:'',
        allUsers:[],
        selectedUserFromAllUsers:"",
        selectedUserFromAddedUsers:"",
        addedUser:[],
        allTeams:[],
        teamName:''
    }
  componentDidMount(){
    this.fetchAllUsers();
    this.fetchAllTeams();
  }
    render() {


            return (<div className='team-manager'>

                        <br></br>
                        <div className="multiple-team-manager-select">

                        <label className="team-manager-label">Team Managers :</label>
                        <br></br>
                            <select id="all-users" onClick={this.onUserNameSelect.bind(this)} name="all-users" multiple>
                              {this.state.allUsers.map(u=>{
                                return (<option key={u} value={u}>{u}</option>);
                              })}
                            </select>

                            <button onClick={this.addUserToTeam.bind(this)}>add</button>
                            <button onClick={this.removeFromTeam.bind(this)}>remove</button>

                            <select id="added-users" onClick={this.onUserNameSelectFromAddedUser.bind(this)} name="selected-users" multiple>
                              {this.state.addedUser.map(u=>{
                                return (<option key={"selected_"+u} value={u}>{u}</option>);
                              })}
                            </select>

                        </div>

                                                <br></br>
                            <select id="select-team" onClick={this.setTeamName.bind(this)} name="select-team">
                              {this.state.allTeams.map(u=>{
                                return (<option key={u} value={u}>{u}</option>);
                              })}
                            </select>
                            <br></br>
                            <button onClick={this.addTeamMembersToTeam.bind(this)}>Add Team Member</button>
                        <div className="team-added-success-message">
                            <span>{this.state.successMsg}</span>
                        </div>
                         <div className="team-added-error-message">
                            <span>{this.state.errorMsg}</span>
                        </div>
                     </div>);
        }

    


        addTeamMembersToTeam(){
            // if(this.state.teamName===""){
            //     this.setState({errorMsg:"Please add team name...!",successMsg:""});
            // }else if(this.state.teamdl===""){
            //     this.setState({errorMsg:"Please add team DL...!",successMsg:""});
            // }else{
            postRequest("/addteammembers",{teammembers:this.state.addedUser,teamname:this.state.teamName},
            (data)=>{
                this.setState({successMsg:"Team member added successfully...!",errorMsg:""});
            });
         //}
           
        }

        fetchAllUsers(){
          getRequest("/getallusers",(data)=>this.setState({allUsers:data}));
        }

        fetchAllTeams(){
            getRequest("/getapppayload/"+this.props.item.appId,(data)=>{
            console.log("Data is ::::");
            console.dir(data);
            this.setState({allTeams:JSON.parse(data.payload)});
            });
          }



        onUserNameSelect(e){
          this.setState({selectedUserFromAllUsers:e.target.value});
        }

        onUserNameSelectFromAddedUser(e){
          this.setState({selectedUserFromAddedUsers:e.target.value});
        }

        addUserToTeam(){
          if(!this.state.addedUser.includes(this.state.selectedUserFromAllUsers)){
              let newAddeddUser = this.state.addedUser;
              newAddeddUser.push(this.state.selectedUserFromAllUsers);
              this.setState({addedUser:newAddeddUser});
          }
        }

        removeFromTeam(){
          let newAddedUsers=[];
          for(let i=0;i<this.state.addedUser.length;i++){
            if(this.state.addedUser[i]!==this.state.selectedUserFromAddedUsers){
              newAddedUsers.push(this.state.addedUser[i]);
            }
          }
          this.setState({addedUser:newAddedUsers});
        }

        setTeamName(e){
            this.setState({teamName:e.target.value});
        }
}
export default TeamManagerPlugin;


