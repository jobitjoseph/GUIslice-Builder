/**
 * Copyright 2020-2022 Csekme Krisztián
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package builder.RibbonMenu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

/**
 * The Class Button.
 */
public class Button extends VirtualObject {

    /** The slim. */
    private boolean slim;
    
    /** The separator. */
    private boolean separator;
    
    /** The actions. */
    private List<ActionListener> actions;
    
    /** The sub menu. */
    private List<Object> subMenu;
    
    /** The pressed. */
    protected boolean pressed;
    
    /** The enabled. */
    protected boolean enabled;

    /** drag enabled flag */
    protected boolean drag_enabled;

    protected ImageIcon drag_image;

  /** after a drag operation this is the value to be dropped */
    protected String transfer_data;

    /** The tooltip, if any. */
    protected ToolTip tooltip;

   /** Optional image to display of button not enabled. */
    protected ImageIcon disabledImage;
    
    /** The action command string fired by the button. */
    protected String actionCommand = null;

    /**
     * Instantiates a new button.
     *
     * @param token
     *          the token
     */
    public Button(String token) {
        super(token);
        this.slim = false;
        this.separator = false;
        this.actions = new ArrayList<>();
        this.subMenu = new ArrayList<>();
        this.pressed = false;
        this.tooltip = null;
        this.disabledImage = null;
        this.enabled = true;
        this.drag_enabled = false;
        this.transfer_data = null;
    }

    /**
     * Creates the separator.
     */
    public void createSeparator() {
        this.separator = true;
    }

  /**
   * getTransfer_data
   * @return our transfer string
   */
  public String getTransfer_data() {
      return transfer_data;
    }

  /**
   * Can this button support drag and drop?
   * @return true if this button can be dragged
   */
  public boolean isDrageEnabled() { return drag_enabled; }

  public void setDragEnabled(String transfer_data, ImageIcon drag_image) {
    this.drag_enabled = true;
    this.transfer_data = transfer_data;
    this.drag_image = drag_image;
  }

  public ImageIcon getDragImage() { return this.drag_image; }

    /**
     * Checks if is slim.
     *
     * @return true, if is slim
     */
    public boolean isSlim() {
        return slim;
    }

    /**
     * Sets the slim.
     *
     * @param slim
     *          the new slim
     */
    public void setSlim(boolean slim) {
        this.slim = slim;
    }

    /**
     * Checks if is separator.
     *
     * @return true, if is separator
     */
    public boolean isSeparator() {
        return separator;
    }


    /**
     * Checks if is pressed.
     *
     * @return true, if is pressed
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * Sets the pressed.
     *
     * @param pressed
     *          the new pressed
     */
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    /**
     * Sets the enabled.
     *
     * @param enabled
     *          the new enabled
     */
    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }
    
    /**
     * Checks if is enabled.
     *
     * @return true, if is enabled
     */
    public boolean isEnabled() {
    	return this.enabled;
    }

    /**
     * Sets the disabled image.
     *
     * @param disabledImage
     *          the new disabled image
     */
    public void setDisabledImage(ImageIcon disabledImage) {
      this.disabledImage = disabledImage;
    }
    
    /**
     * Convert ImageIcon to grayscale keep alpha channel.
     *
     * @param image
     *          as original ImageIcon
     * @return image as grayscaled ImageIcon
     */
    protected static ImageIcon convertToGrayScale(ImageIcon image) {
    	BufferedImage source = (BufferedImage)image.getImage();
    	BufferedImage img = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
    	for (int x=0; x<img.getWidth(); x++) {
    		for (int y=0; y<img.getHeight(); y++) {
    	
    			int p = source.getRGB(x,y); //get pixel
    			int a = (p>>24)&0xff; //alpha chanel
    			int r = (p>>16)&0xff; //red chanel
    			int g = (p>>8)&0xff; //green chanel
    			int b = p&0xff; // blue chanel
    			// https://en.wikipedia.org/wiki/Grayscale
    			// use luma coding
    			int avg = (int)(r * 0.299) + (int)(g * 0.587) + (int)(b * 0.114);
    			//use 50% transparency on alpha channel  
    			p = ((int)(a*.5)<<24) | (avg<<16) | (avg<<8) | avg;
    			// set new pixel
    			img.setRGB(x, y, p);	
    	
    		} //end for button width
    	} //end for button height
    	
    	return new ImageIcon(img);
    }
    
    /**
     * Button image.
     *
     * @param image
     *          as ImageIcon
     */
    @Override
    public void setImage(ImageIcon image) {
        super.setImage(image);
        
        this.setWidth(image.getIconWidth());
        this.setHeight(image.getIconHeight());
    }

   /**
    * getImage
    *
    * @see VirtualObject#getImage()
    */
   @Override
    public ImageIcon getImage() {
      if (!enabled) {
        //If no default disabled image create it from original image
        if (disabledImage==null) {
          disabledImage = convertToGrayScale(super.getImage());
        }
        return disabledImage;
      }
      return super.getImage();
    }

    /**
     * Adds the action listener.
     *
     * @param a
     *          the a
     */
    public void addActionListener(ActionListener a) {
        actions.add(a);
    }

    /**
     * Sets the action command.
     *
     * @param actionCommand
     *          the new action command
     */
    public void setActionCommand(String actionCommand) {
      this.actionCommand = actionCommand;
    }

    /**
     * Gets the action command.
     *
     * @return the action command
     */
    public String getActionCommand() {
      return actionCommand;
    }

    /**
     * Adds the sub menu.
     *
     * @param a
     *          the a
     */
    public void addSubMenu(JMenuItem a) {
        a.setForeground(Color.DARK_GRAY);
        subMenu.add(a);
    }

    /**
     * Adds the sub menu.
     *
     * @param a
     *          the a
     */
    public void addSubMenu(JCheckBoxMenuItem a) {
        subMenu.add(a);
    }


    /**
     * Adds the sub menu.
     *
     * @param a
     *          the a
     * @param caption
     *          the caption
     */
    public void addSubMenu(ActionListener a, String caption) {
        RibbonMenuItem m = new RibbonMenuItem(caption);
        m.addActionListener(a);
        subMenu.add(m);
    }

    /**
     * Adds the tool tip.
     *
     * @param text
     *          the text
     */
    public void addToolTip(String text) {
      this.tooltip = new ToolTip(text);
    }
    
    /**
     * Gets the tool tip.
     *
     * @return the tool tip
     */
    public String getToolTip() {
      if (tooltip==null)
        return null;
      return this.tooltip.getText();
    }
    
    /**
     * Gets the sub menu list.
     *
     * @return the sub menu list
     */
    public List<Object> getSubMenuList() {
        return subMenu;
    }

    /**
     * Checks for drop down.
     *
     * @return <code>true</code>, if successful
     */
    public boolean hasDropDown() {
        return subMenu.size() == 0 ? false : true;
    }

    /**
     * Fire action.
     *
     * @param event
     *          the event
     */
    public void fireAction(ActionEvent event) {
      if (enabled) {
        ActionEvent e = event;
        if (actionCommand != null) {
          e = new ActionEvent(this,
              ActionEvent.ACTION_PERFORMED,
              actionCommand,
              event.getWhen(),
              event.getModifiers());
        }
        for (ActionListener a : actions) {
           a.actionPerformed(e);
        }
      }
    }

}
