 /* Copyright (c) 2007 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the GNU Lesser General Public License, Version 2.1. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt. The Original Code is Pentaho 
 * Data Integration.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.*/

/*
 * Created on 18-mei-2003
 *
 */

package org.pentaho.di.ui.trans.steps.clonerow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.core.Const;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.ui.trans.step.BaseStepDialog;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.steps.clonerow.CloneRowMeta;
import org.pentaho.di.trans.steps.clonerow.Messages;

public class CloneRowDialog extends BaseStepDialog implements StepDialogInterface
{
	private CloneRowMeta input;

	// nr clones
	private Label wlnrClone;
	private TextVar wnrClone;
	private FormData fdlnrClone, fdnrClone;
	
	private Label wlcloneFlagField;
	private TextVar wcloneFlagField;
	private FormData fdlcloneFlagField, fdcloneFlagField;
	
	private Label        wladdCloneFlag;
	private Button       waddCloneFlag;
	private FormData     fdladdCloneFlag, fdaddCloneFlag;
	
	public CloneRowDialog(Shell parent, Object in, TransMeta tr, String sname)
	{
		super(parent, (BaseStepMeta)in, tr, sname);
		input=(CloneRowMeta)in;
	}

	public String open()
	{
		Shell parent = getParent();
		Display display = parent.getDisplay();

		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
 		props.setLook(shell);
 		setShellImage(shell, input);
        
		ModifyListener lsMod = new ModifyListener() 
		{
			public void modifyText(ModifyEvent e) 
			{
				input.setChanged();
			}
		};
		changed = input.hasChanged();

		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth  = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;

		shell.setLayout(formLayout);
		shell.setText(Messages.getString("CloneRowDialog.Shell.Title")); //$NON-NLS-1$
		
		int middle = props.getMiddlePct();
		int margin = Const.MARGIN;

		// Stepname line
		wlStepname=new Label(shell, SWT.RIGHT);
		wlStepname.setText(Messages.getString("CloneRowDialog.Stepname.Label")); //$NON-NLS-1$
 		props.setLook(wlStepname);
		fdlStepname=new FormData();
		fdlStepname.left = new FormAttachment(0, 0);
		fdlStepname.right= new FormAttachment(middle, -margin);
		fdlStepname.top  = new FormAttachment(0, margin);
		wlStepname.setLayoutData(fdlStepname);
		wStepname=new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wStepname.setText(stepname);
 		props.setLook(wStepname);
		wStepname.addModifyListener(lsMod);
		fdStepname=new FormData();
		fdStepname.left = new FormAttachment(middle, 0);
		fdStepname.top  = new FormAttachment(0, margin);
		fdStepname.right= new FormAttachment(100, 0);
		wStepname.setLayoutData(fdStepname);
		
		// Number of clones line
		wlnrClone = new Label(shell, SWT.RIGHT);
		wlnrClone.setText(Messages.getString("CloneRowDialog.nrClone.Label"));
		props.setLook(wlnrClone);
		fdlnrClone = new FormData();
		fdlnrClone.left = new FormAttachment(0, 0);
		fdlnrClone.right = new FormAttachment(middle, -margin);
		fdlnrClone.top = new FormAttachment(wStepname, margin*2);
		wlnrClone.setLayoutData(fdlnrClone);

		wnrClone = new TextVar(transMeta,shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		props.setLook(wnrClone);
		wnrClone.setToolTipText(Messages.getString("CloneRowDialog.nrClone.Tooltip"));
		wnrClone.addModifyListener(lsMod);
		fdnrClone = new FormData();
		fdnrClone.left = new FormAttachment(middle, 0);
		fdnrClone.top = new FormAttachment(wStepname, margin*2);
		fdnrClone.right = new FormAttachment(100, 0);
		wnrClone.setLayoutData(fdnrClone);
		
		// add clone flag?
		wladdCloneFlag=new Label(shell, SWT.RIGHT);
		wladdCloneFlag.setText(Messages.getString("CloneRowDialog.addCloneFlag.Label"));
		props.setLook(wladdCloneFlag);
		fdladdCloneFlag=new FormData();
		fdladdCloneFlag.left  = new FormAttachment(0, 0);
		fdladdCloneFlag.top   = new FormAttachment(wnrClone, margin);
		fdladdCloneFlag.right = new FormAttachment(middle, -margin);
		wladdCloneFlag.setLayoutData(fdladdCloneFlag);
		waddCloneFlag=new Button(shell, SWT.CHECK);
		waddCloneFlag.setToolTipText(Messages.getString("CloneRowDialog.addCloneFlag.Tooltip"));
 		props.setLook(waddCloneFlag);
		fdaddCloneFlag=new FormData();
		fdaddCloneFlag.left  = new FormAttachment(middle, 0);
		fdaddCloneFlag.top   = new FormAttachment(wnrClone, margin);
		fdaddCloneFlag.right = new FormAttachment(100, 0);
		waddCloneFlag.setLayoutData(fdaddCloneFlag);
		SelectionAdapter lsSelR = new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent arg0)
            {
                input.setChanged();
                activeaddCloneFlag();
            }
        };
		waddCloneFlag.addSelectionListener(lsSelR);
		
		// clone falg field line
		wlcloneFlagField = new Label(shell, SWT.RIGHT);
		wlcloneFlagField.setText(Messages.getString("CloneRowDialog.cloneFlagField.Label"));
		props.setLook(wlcloneFlagField);
		fdlcloneFlagField = new FormData();
		fdlcloneFlagField.left = new FormAttachment(0, 0);
		fdlcloneFlagField.right = new FormAttachment(middle, -margin);
		fdlcloneFlagField.top = new FormAttachment(waddCloneFlag, margin*2);
		wlcloneFlagField.setLayoutData(fdlcloneFlagField);

		wcloneFlagField = new TextVar(transMeta,shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		props.setLook(wcloneFlagField);
		wcloneFlagField.setToolTipText(Messages.getString("CloneRowDialog.cloneFlagField.Tooltip"));
		wcloneFlagField.addModifyListener(lsMod);
		fdcloneFlagField = new FormData();
		fdcloneFlagField.left = new FormAttachment(middle, 0);
		fdcloneFlagField.top = new FormAttachment(waddCloneFlag, margin*2);
		fdcloneFlagField.right = new FormAttachment(100, 0);
		wcloneFlagField.setLayoutData(fdcloneFlagField);
		
		// Some buttons
		wOK=new Button(shell, SWT.PUSH);
		wOK.setText(Messages.getString("System.Button.OK")); //$NON-NLS-1$
		wCancel=new Button(shell, SWT.PUSH);
		wCancel.setText(Messages.getString("System.Button.Cancel")); //$NON-NLS-1$

		setButtonPositions(new Button[] { wOK, wCancel }, margin, wcloneFlagField);

		// Add listeners
		lsCancel   = new Listener() { public void handleEvent(Event e) { cancel(); } };
		lsOK       = new Listener() { public void handleEvent(Event e) { ok();     } };
		
		wCancel.addListener(SWT.Selection, lsCancel);
		wOK.addListener    (SWT.Selection, lsOK    );
		
		lsDef=new SelectionAdapter() { public void widgetDefaultSelected(SelectionEvent e) { ok(); } };
		
		wStepname.addSelectionListener( lsDef );
		
		// Detect X or ALT-F4 or something that kills this window...
		shell.addShellListener(	new ShellAdapter() { public void shellClosed(ShellEvent e) { cancel(); } } );


		// Set the shell size, based upon previous time...
		setSize();
		
		getData();
		activeaddCloneFlag();
		input.setChanged(changed);
	
		shell.open();
		while (!shell.isDisposed())
		{
				if (!display.readAndDispatch()) display.sleep();
		}
		return stepname;
	}
	private void activeaddCloneFlag()
	{
		wcloneFlagField.setEnabled(waddCloneFlag.getSelection());
	}
	/**
	 * Copy information from the meta-data input to the dialog fields.
	 */ 
	public void getData()
	{
		wStepname.selectAll();
		if (input.getNrClones() !=null)   wnrClone.setText(input.getNrClones());
		waddCloneFlag.setSelection(input.isAddCloneFlag());
		if (input.getCloneFlagField() !=null)   wcloneFlagField.setText(input.getCloneFlagField());
	}
	
	private void cancel()
	{
		stepname=null;
		input.setChanged(changed);
		dispose();
	}
	
	private void ok()
	{
		if (Const.isEmpty(wStepname.getText())) return;

		stepname = wStepname.getText(); // return value
		input.setNrClones(Const.toInt(wnrClone.getText(), 0)+"" );
		input.setAddCloneFlag(waddCloneFlag.getSelection());
		input.setCloneFlagField(wcloneFlagField.getText());
		dispose();
	}
}
