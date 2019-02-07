/**
 * Copyright @ 2019 Mukha Dzmitry
 * Copyright @ 2011 Quan Nguyen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.tessboxeditor.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import net.sourceforge.tessboxeditor.Gui;
import net.sourceforge.tessboxeditor.datamodel.*;
import net.sourceforge.vietocr.util.Utils;

public class JImageLabel extends JLabel {

    private TessBoxCollection boxes;
    private JTable table;
    private boolean boxClickAction;
    private TessBox selectedBox;
    
    private JSpinner jSpinnerX;
    private JSpinner jSpinnerY;
    private JSpinner jSpinnerW;
    private JSpinner jSpinnerH;
    
    private boolean resizableLeftBound = false;
    private boolean resizableRightBound = false;
    private boolean resizableTopBound = false;
    private boolean resizableBottomBound = false;
    
    private boolean resizableBox = false;
    
    private boolean isResizingBox = false;
    
    private boolean isMovingViewportView = false;
    private Point startDraggingPoint;

    private Cursor openhand;
    private Cursor closedhand;
    
    /** Creates a new instance of JImageLabel */
    public JImageLabel() {
    	Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image image = toolkit.getImage(getClass().getResource("/net/sourceforge/tessboxeditor/icons/openhand.gif"));
    	openhand = toolkit.createCustomCursor(image , new Point(0,0), "openhand");
    	image = toolkit.getImage(getClass().getResource("/net/sourceforge/tessboxeditor/icons/closedhand.gif"));
    	closedhand = toolkit.createCustomCursor(image , new Point(0,0), "closedhand");
    	
    	MouseAdapter mouseAdapter = new MouseAdapter() {
    		@Override
            public void mouseMoved(MouseEvent e) {
    			if (boxes == null || selectedBox == null) {
                    return;
                }
    			
    			Point p = e.getPoint();
        		Rectangle rect = selectedBox.getRect();
        		int leftBound = rect.x;
        		int rightBound = rect.x + rect.width;
        		int topBound = rect.y;
        		int bottomBound = rect.y + rect.height;
        		resizableBox = true;
        	    resizableLeftBound = false;
        	    resizableRightBound = false;
        	    resizableTopBound = false;
        	    resizableBottomBound = false;
            	if (leftBound - 4 < p.x && leftBound + 4 > p.x) {
            		setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
            		resizableLeftBound = true;
            	} else if (rightBound - 4 < p.x && rightBound + 4 > p.x) {
            		setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
            		resizableRightBound = true;
            	} else if (topBound - 4 < p.y && topBound + 4 > p.y) {
            		setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
            		resizableTopBound = true;
            	} else if (bottomBound - 4 < p.y && bottomBound + 4 > p.y) {
            		setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
            		resizableBottomBound = true;
            	} else {
            		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            		resizableBox = false;
            	}
            }
        	
            @Override
            public void mousePressed(MouseEvent me) {
            	if (me.getButton() == MouseEvent.BUTTON3) {
            		setCursor(closedhand);
            		isMovingViewportView = true;
            		startDraggingPoint = me.getPoint();
            		return;
    			}
            	
                if (boxes == null) {
                    return;
                }
                
                if (resizableBox) {
                	isResizingBox = true;
                	return;
                }

                TessBox box = boxes.hitObject(me.getPoint());
                if (box == null) {
                    if (!me.isControlDown()) {
                        boxes.deselectAll();
                        repaint();
                        table.clearSelection();
                    }
                    selectedBox = null;
                } else {
                    if (!me.isControlDown()) {
                        boxes.deselectAll();
                        table.clearSelection();
                    }
                    selectedBox = box;
                    box.setSelected(!box.isSelected()); // toggle selection
                    repaint();
                    // select corresponding table rows
                    boxClickAction = true;
                    java.util.List<TessBox> boxesOfCurPage = boxes.toList(); // boxes of current page
                    for (TessBox selectedBoxes : boxes.getSelectedBoxes()) {
                        int index = boxesOfCurPage.indexOf(selectedBoxes);
                        table.addRowSelectionInterval(index, index);
                        Rectangle rect = table.getCellRect(index, 0, true);
                        table.scrollRectToVisible(rect);
                    }
                    boxClickAction = false;
                }
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
            	if (isMovingViewportView) {
            		Rectangle visibleRect = getVisibleRect();
            		int deltaX = startDraggingPoint.x - e.getPoint().x;
            		int deltaY = startDraggingPoint.y - e.getPoint().y;
            		visibleRect.x += deltaX;
            		visibleRect.y += deltaY;
            		scrollRectToVisible(visibleRect);
            		return;
            	}
            	
            	if (!isResizingBox) {
            		return;
            	}
            	
            	Rectangle rect = selectedBox.getRect();
            	if (resizableLeftBound) {
            		rect.width += rect.x - e.getPoint().x;
            		rect.x = e.getPoint().x;
            	} else if (resizableRightBound) {
            		rect.width -= rect.x + rect.width - e.getPoint().x;
            	} else if (resizableTopBound) {
            		rect.height += rect.y - e.getPoint().y;
            		rect.y = e.getPoint().y;
            	} else {
            		rect.height -= rect.y + rect.height - e.getPoint().y;
            	}
				jSpinnerX.setValue(rect.x);
				jSpinnerY.setValue(rect.y);
				jSpinnerW.setValue(rect.width);
				jSpinnerH.setValue(rect.height);
            	repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
            	setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            	isMovingViewportView = false;
            	isResizingBox = false;
            }
    	};
    	
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    @Override
    public void paintComponent(Graphics g) {
        // automatically called when repaint
        super.paintComponent(g);

        if (boxes == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        boolean resetColor = false;
//        int height = getHeight();

        for (TessBox box : boxes.toList()) {
            if (box.isSelected()) {
                g2d.setStroke(new BasicStroke(2));
                g2d.setColor(Color.RED);
                resetColor = true;
            }
            Rectangle rect = box.getRect();
            
            ResizableRectangle r = new ResizableRectangle();
            g2d.draw(rect);
//            g2d.drawRect(rect.x, height - rect.y - rect.height, rect.width, rect.height);
            if (resetColor) {
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.BLUE);
                resetColor = false;
            }

//            g2d.setColor(Color.MAGENTA);
//            g2d.drawString(box.getChrs(), rect.x, rect.y + rect.height + 15);
//            g2d.setColor(Color.BLUE);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        if (this.boxes != null) {
            TessBox curBox = this.boxes.hitObject(new Point(x, y));
            if (curBox != null) {
                String curChrs = curBox.getChrs();
                setToolTipText(String.format("<html><h1><font face=\"%s\" >%s</font> : %s</h1></html>", this.getFont().getName(), curChrs, Utils.toHex(curChrs)));
            } else {
                setToolTipText(null);
            }
        }
        return super.contains(x, y);
    }

    public void setBoxes(TessBoxCollection boxes) {
        this.boxes = boxes;
        repaint();
    }

    /**
     * @param table the table to set
     */
    public void setTable(JTable table) {
        this.table = table;
    }

    /**
     * @return the boxClickAction
     */
    public boolean isBoxClickAction() {
        return boxClickAction;
    }

	public final JSpinner getjSpinnerX() {
		return jSpinnerX;
	}

	public final void setjSpinnerX(JSpinner jSpinnerX) {
		this.jSpinnerX = jSpinnerX;
	}

	public final JSpinner getjSpinnerY() {
		return jSpinnerY;
	}

	public final void setjSpinnerY(JSpinner jSpinnerY) {
		this.jSpinnerY = jSpinnerY;
	}

	public final JSpinner getjSpinnerW() {
		return jSpinnerW;
	}

	public final void setjSpinnerW(JSpinner jSpinnerW) {
		this.jSpinnerW = jSpinnerW;
	}

	public final JSpinner getjSpinnerH() {
		return jSpinnerH;
	}

	public final void setjSpinnerH(JSpinner jSpinnerH) {
		this.jSpinnerH = jSpinnerH;
	}
}
