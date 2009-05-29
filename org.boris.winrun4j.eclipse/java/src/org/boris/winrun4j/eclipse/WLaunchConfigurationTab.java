/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.winrun4j.eclipse;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jdt.internal.debug.ui.SWTFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class WLaunchConfigurationTab extends AbstractLaunchConfigurationTab implements
        SelectionListener
{
    // UI elements
    private Combo logLevelCombo;

    public WLaunchConfigurationTab() {
    }

    public Image getImage() {
        return WActivator.getLauncherImage();
    }

    public void createControl(Composite parent) {
        Composite comp = SWTFactory.createComposite(parent, parent.getFont(), 1, 1,
                GridData.FILL_BOTH);
        ((GridLayout) comp.getLayout()).verticalSpacing = 0;
        createLogEditor(comp);
        setControl(comp);
    }

    private void createLogEditor(Composite parent) {
        Font font = parent.getFont();
        Group group = new Group(parent, SWT.NONE);
        group.setText("Logging");
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        group.setLayoutData(gd);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        group.setLayout(layout);
        group.setFont(font);
        Text t = new Text(group, SWT.NULL);
        t.setFont(font);
        t.setText("Level:");
        this.logLevelCombo = new Combo(group, SWT.DROP_DOWN | SWT.READ_ONLY);
        this.logLevelCombo.setItems(new String[] { "info", "warning", "error", "none" });
        this.logLevelCombo.setFont(font);
        this.logLevelCombo.addSelectionListener(this);
    }

    public String getName() {
        return "WinRun4J";
    }

    public void initializeFrom(ILaunchConfiguration configuration) {
        try {
            UIHelper.select(logLevelCombo, configuration.getAttribute(
                    IWinRun4JLaunchConfigurationConstants.PROP_LOG_LEVEL, "info"));
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        String text = UIHelper.getSelection(logLevelCombo);
        if (text != null) {
            configuration.setAttribute(IWinRun4JLaunchConfigurationConstants.PROP_LOG_LEVEL, text);
        }
    }

    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(IWinRun4JLaunchConfigurationConstants.PROP_LOG_LEVEL, "info");
    }

    public void widgetDefaultSelected(SelectionEvent e) {
    }

    public void widgetSelected(SelectionEvent e) {
        setDirty(true);
        updateLaunchConfigurationDialog();
    }
}