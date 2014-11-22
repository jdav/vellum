package com.malleamus.vellum.console;

import com.malleamus.speckle.Context;
import com.malleamus.speckle.InvitationView;
import com.malleamus.speckle.PromptingView;
import com.malleamus.speckle.SpeckleException;
import com.malleamus.speckle.UseCase;

/**
 * This class should be set as the STARTUP_USE_CASE_CLASS in the config 
 * file that is used as the argument to com.malleamus.speckle.Executive.
 * 
 * Testing the change...
 * 
 * @author jeff
 *
 */
public class StartupTheConsole implements UseCase {

	@Override
	public void setContext(Context context) throws SpeckleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Context getContext() throws SpeckleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAuthenticated() throws SpeckleException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAuthorized() throws SpeckleException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PromptingView getOpening() throws SpeckleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvitationView getRebuff() throws SpeckleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute() throws SpeckleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InvitationView getClosing() throws SpeckleException {
		// TODO Auto-generated method stub
		return null;
	}

}
