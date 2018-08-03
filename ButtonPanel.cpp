//
//  ButtonPanel.cpp
//  CS349_A2
//
//  Created by Mengqi Liu on 2/3/2014.
//
//

#include "ButtonPanel.h"
#include "Button.h"
#include "A2WidgetIdentifiers.h"

using namespace cs349;

ButtonPanel::ButtonPanel(const string & text1,const string & text2,const string & text3, const string & text4,const string & text5)
: Panel(BUTTON_PANEL_ID)
{
    Button* button1 = new Button(GO_TO_START_BUTTON_ID,text1);
    Button* button2 = new Button(PREVIOUS_FRAME_BUTTON_ID,text2);
    Button* button3 = new Button(PLAY_BUTTON_ID,text3);
    Button* button4 = new Button(NEXT_FRAME_BUTTON_ID,text4);
    Button* button5 = new Button(GO_TO_END_BUTTON_ID,text5);
    
    this->SetBounds(Rectangle(330, 300, 500, 400));
    button1->SetBounds(Rectangle(340, 320, 380, 350));
    button5->SetBounds(Rectangle(340, 360, 380, 390));
    
    button2->SetBounds(Rectangle(420, 320, 480, 350));
    button4->SetBounds(Rectangle(420, 360, 480, 390));
    
    button3->SetBounds(Rectangle(380,340,420,370));
                       
    
    this->AddComponent(button1);
    this->AddComponent(button2);
    this->AddComponent(button3);
    this->AddComponent(button4);
    this->AddComponent(button5);
    
    
    button1->SetVisible(true);
    button2->SetVisible(true);
    button3->SetVisible(true);
    button4->SetVisible(true);
    button5->SetVisible(true);
                       
}
