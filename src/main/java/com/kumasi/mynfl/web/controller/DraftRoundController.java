/*
 * Created on 5 Jun 2016 ( Time 14:55:48 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//--- Common classes
import com.kumasi.mynfl.web.common.AbstractController;
import com.kumasi.mynfl.web.common.FormMode;
import com.kumasi.mynfl.web.common.Message;
import com.kumasi.mynfl.web.common.MessageType;

//--- Entities
import com.kumasi.mynfl.domain.DraftRound;
import com.kumasi.mynfl.domain.DraftTeam;

//--- Services 
import com.kumasi.mynfl.business.service.DraftRoundService;
import com.kumasi.mynfl.business.service.DraftTeamService;

//--- List Items 
import com.kumasi.mynfl.web.listitem.DraftTeamListItem;

/**
 * Spring MVC controller for 'DraftRound' management.
 */
@Controller
@RequestMapping("/draftRound")
public class DraftRoundController extends AbstractController {

	//--- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "draftRound";
	private static final String MAIN_LIST_NAME   = "list";

	//--- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM   = "draftRound/form";
	private static final String JSP_LIST   = "draftRound/list";

	//--- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE   = "/draftRound/create";
	private static final String SAVE_ACTION_UPDATE   = "/draftRound/update";

	//--- Main entity service
	@Resource
    private DraftRoundService draftRoundService; // Injected by Spring
	//--- Other service(s)
	@Resource
    private DraftTeamService draftTeamService; // Injected by Spring

	//--------------------------------------------------------------------------------------
	/**
	 * Default constructor
	 */
	public DraftRoundController() {
		super(DraftRoundController.class, MAIN_ENTITY_NAME );
		log("DraftRoundController created.");
	}

	//--------------------------------------------------------------------------------------
	// Spring MVC model management
	//--------------------------------------------------------------------------------------
	/**
	 * Populates the combo-box "items" for the referenced entity "DraftTeam"
	 * @param model
	 */
	private void populateListOfDraftTeamItems(Model model) {
		List<DraftTeam> list = draftTeamService.findAll();
		List<DraftTeamListItem> items = new LinkedList<DraftTeamListItem>();
		for ( DraftTeam draftTeam : list ) {
			items.add(new DraftTeamListItem( draftTeam ) );
		}
		model.addAttribute("listOfDraftTeamItems", items ) ;
	}


	/**
	 * Populates the Spring MVC model with the given entity and eventually other useful data
	 * @param model
	 * @param draftRound
	 */
	private void populateModel(Model model, DraftRound draftRound, FormMode formMode) {
		//--- Main entity
		model.addAttribute(MAIN_ENTITY_NAME, draftRound);
		if ( formMode == FormMode.CREATE ) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE); 			
			//--- Other data useful in this screen in "create" mode (all fields)
			populateListOfDraftTeamItems(model);
		}
		else if ( formMode == FormMode.UPDATE ) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE); 			
			//--- Other data useful in this screen in "update" mode (only non-pk fields)
			populateListOfDraftTeamItems(model);
		}
	}

	//--------------------------------------------------------------------------------------
	// Request mapping
	//--------------------------------------------------------------------------------------
	/**
	 * Shows a list with all the occurrences of DraftRound found in the database
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model) {
		log("Action 'list'");
		List<DraftRound> list = draftRoundService.findAll();
		model.addAttribute(MAIN_LIST_NAME, list);		
		return JSP_LIST;
	}

	/**
	 * Shows a form page in order to create a new DraftRound
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(Model model) {
		log("Action 'formForCreate'");
		//--- Populates the model with a new instance
		DraftRound draftRound = new DraftRound();	
		populateModel( model, draftRound, FormMode.CREATE);
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to update an existing DraftRound
	 * @param model Spring MVC model
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/form/{id}")
	public String formForUpdate(Model model, @PathVariable("id") Integer id ) {
		log("Action 'formForUpdate'");
		//--- Search the entity by its primary key and stores it in the model 
		DraftRound draftRound = draftRoundService.findById(id);
		populateModel( model, draftRound, FormMode.UPDATE);		
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param draftRound  entity to be created
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/create" ) // GET or POST
	public String create(@Valid DraftRound draftRound, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'create'");
		try {
			if (!bindingResult.hasErrors()) {
				DraftRound draftRoundCreated = draftRoundService.create(draftRound); 
				model.addAttribute(MAIN_ENTITY_NAME, draftRoundCreated);

				//---
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				return redirectToForm(httpServletRequest, draftRound.getId() );
			} else {
				populateModel( model, draftRound, FormMode.CREATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			log("Action 'create' : Exception - " + e.getMessage() );
			messageHelper.addException(model, "draftRound.error.create", e);
			populateModel( model, draftRound, FormMode.CREATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'UPDATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param draftRound  entity to be updated
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/update" ) // GET or POST
	public String update(@Valid DraftRound draftRound, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'update'");
		try {
			if (!bindingResult.hasErrors()) {
				//--- Perform database operations
				DraftRound draftRoundSaved = draftRoundService.update(draftRound);
				model.addAttribute(MAIN_ENTITY_NAME, draftRoundSaved);
				//--- Set the result message
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				log("Action 'update' : update done - redirect");
				return redirectToForm(httpServletRequest, draftRound.getId());
			} else {
				log("Action 'update' : binding errors");
				populateModel( model, draftRound, FormMode.UPDATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			messageHelper.addException(model, "draftRound.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage() );
			populateModel( model, draftRound, FormMode.UPDATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param redirectAttributes
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}") // GET or POST
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
		log("Action 'delete'" );
		try {
			draftRoundService.delete( id );
			//--- Set the result message
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));	
		} catch(Exception e) {
			messageHelper.addException(redirectAttributes, "draftRound.error.delete", e);
		}
		return redirectToList();
	}

}