/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.Stack;

/**
 *
 * @author Eleonore
 */
public class CommandList {
    private Stack StackUndo;
    private Stack StackRedo;
    
    public CommandList(){
           StackUndo = new Stack();
           StackRedo = new Stack();
    }

    public Stack getStackUndo() {
        return StackUndo;
    }

    public Stack getStackRedo() {
        return StackRedo;
    }
    
    
    public void AddComandUndo(Command command){
         StackUndo.push ( command );
    }
 
        public void AddComandRedo(Command command){
         StackRedo.push ( command );
        }
        

        
                public void Redo(){
                    Command command;
                    command = (Command) StackRedo.pop();
                    command.Redo();
                }
                
                public void Undo(){
                     Command command;
                    command = (Command) StackUndo.pop();
                    command.Undo();
                }
}
