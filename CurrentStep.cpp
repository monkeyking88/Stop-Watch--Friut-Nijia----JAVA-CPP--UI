//
//  CurrentStep.cpp
//  CS349_A2
//
//  Created by Mengqi Liu on 2/3/2014.
//
//

#include "CurrentStep.h"
#include "Slider.h"
#include "A2WidgetIdentifiers.h"
#include "Label.h"
#include "Slider.h"

using namespace std;
using namespace cs349;

CurrentStep::CurrentStep(const string & text)
: Panel(CURRENT_TURTLE_STEP_PANEL_ID)
{
    Label* CurrentStepLabel = new Label(CURRENT_TURTLE_STEP_LABEL_ID,text);
    Slider* CurrentStepSlider = new Slider(CURRENT_TURTLE_STEP_SLIDER_ID,Slider::HORIZONTAL_SLIDER);
    
    this->SetBounds(Rectangle(330, 150, 500, 300));
    CurrentStepSlider->SetBounds(Rectangle(340, 170, 480, 250));
    CurrentStepLabel->SetBounds(Rectangle(340, 280, 480, 200));
    
    this->AddComponent(CurrentStepLabel);
    this->AddComponent(CurrentStepSlider);
    
    
    CurrentStepLabel->SetVisible(true);
    CurrentStepSlider->SetVisible(true);
}
