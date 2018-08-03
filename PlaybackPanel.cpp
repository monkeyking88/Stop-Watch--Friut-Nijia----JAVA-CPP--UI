//
//  PlaybackPanel.cpp
//  CS349_A2
//
//  Created by Mengqi Liu on 2/3/2014.
//
//

#include "PlaybackPanel.h"
#include "Slider.h"
#include "A2WidgetIdentifiers.h"
#include "Label.h"

using namespace cs349;

PlaybackPanel::PlaybackPanel(const string & text)
: Panel(PLAYBACK_PANEL_ID)
{
    Label* PlaybackLabel = new Label(PLAYBACK_OUTPUT_LABEL_ID,text);
    Slider* PlaybackSlider = new Slider(PLAYBACK_SLIDER_ID,Slider::HORIZONTAL_SLIDER);
    
    this->SetBounds(Rectangle(330, 0, 500, 150));
    PlaybackSlider->SetBounds(Rectangle(340, 20, 480, 100));
    PlaybackLabel->SetBounds(Rectangle(340, 130, 480, 150));
    
    this->AddComponent(PlaybackSlider);
    this->AddComponent(PlaybackLabel);

    
    PlaybackLabel->SetVisible(true);
    PlaybackSlider->SetVisible(true);
}
