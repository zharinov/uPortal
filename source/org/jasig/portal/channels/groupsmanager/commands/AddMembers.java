/**
 * Copyright � 2001 The JA-SIG Collaborative.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. Redistributions of any form whatsoever must retain the following
 *    acknowledgment:
 *    "This product includes software developed by the JA-SIG Collaborative
 *    (http://www.jasig.org/)."
 *
 * THIS SOFTWARE IS PROVIDED BY THE JA-SIG COLLABORATIVE "AS IS" AND ANY
 * EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE JA-SIG COLLABORATIVE OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */


package  org.jasig.portal.channels.groupsmanager.commands;

/**
 * <p>Title: uPortal</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Columbia University</p>
 * @author Don Fracapane
 * @version 2.0
 */
import  java.util.*;
import  org.jasig.portal.ChannelStaticData;
import  org.jasig.portal.channels.groupsmanager.*;
import  org.jasig.portal.groups.IEntityGroup;
import  org.jasig.portal.groups.IGroupMember;
import  org.jasig.portal.groups.GroupsException;
import org.jasig.portal.services.LogService;
import org.jasig.portal.services.GroupService;
import  org.w3c.dom.Element;
import  org.apache.xerces.dom.DocumentImpl;


/** This command sets the id of the parent group (ie. the group to which child
 *  members will be added). Control is then passed to a selection view where
 *  the child group members will be selected for addition. When the selection
 *  has been completed by the user, the DoneWithSelection command will be
 *  invoked where the selected children group members are actually processed.
 *  Alternatively, the CancelSelection command is invoked to cancel the
 *  selection process and reset the mode and view control parameters.
 */
public class AddMembers extends org.jasig.portal.channels.groupsmanager.commands.GroupsManagerCommand {

   /**
    * put your documentation comment here
    */
   public AddMembers () {
   }

   /**
    * put your documentation comment here
    * @param runtimeData
    * @param staticData
    */
   public void execute (org.jasig.portal.ChannelRuntimeData runtimeData, ChannelStaticData staticData) {
      Utility.logMessage("DEBUG", "AddMembers::execute(): Start");
      String parentAddElemId = getCommandIds(runtimeData);
      // if not IPerson group, then set view root to root for requested type
      try{
        IGroupMember pg = Utility.retrieveGroupMemberForElementId(this.getXmlDoc(staticData),parentAddElemId);
        if (!(pg.getEntityType().getName().equals(GroupService.EVERYONE))){
          runtimeData.setParameter("grpViewKey",GroupService.getRootGroup(pg.getEntityType()).getKey());
        }
      }
      catch(Exception e){
        LogService.instance().log(LogService.ERROR,e);
      }
      Utility.logMessage("DEBUG", "AddMembers::execute(): Uid of parent element = " +
            parentAddElemId);
      staticData.setParameter("groupParentId", parentAddElemId);
   }
}



