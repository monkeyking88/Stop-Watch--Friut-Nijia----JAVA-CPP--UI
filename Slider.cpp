#include "Slider.h"
#include "XWindow.h"
#include "Logging.h"
#include "A2WidgetIdentifiers.h"
#include "TurtleGraphics.h"
#include <sstream>
#include <algorithm>

using namespace cs349;
using namespace std;

Slider::Slider(const string & name, OrientationType orientation)
  : Component(name)
{
  lowerBound = 0;
  upperBound = 0;
  curValue = 0;
  lowerBoundLabel = "";
  upperBoundLabel = "";
  thumbLabel = "";
  labelSuffix = "";
  this->orientation = orientation;

  if (name == PLAYBACK_SLIDER_ID){
    this->type = PLAYBACK;
    this->labelSuffix = "x";
  }
  else if (name == CURRENT_TURTLE_STEP_SLIDER_ID){
    this->type = CURRENTSTEP;
    this->labelSuffix = "";
  }
}

void Slider::AddValueListener(ValueListener* l) {
  this->valueListenerList.push_back(l);
}

void Slider::RemoveValueListener(ValueListener* l) {
  remove(this->valueListenerList.begin(), this->valueListenerList.end(), l);
}

void Slider::NotifyListeners(){
  //LOG(INFO) << "SLIDER " << this->GetName() << " calling for action";
  for (vector<ValueListener*>::iterator i = this->valueListenerList.begin(); i != this->valueListenerList.end(); i++) {
    (*i)->ValueChanged(this);
  }
}

int Slider::GetMinimum() const {
  return lowerBound;
}

int Slider::GetMaximum() const {
  return upperBound;
}

int Slider::GetCurValue() const {
  return curValue;
}

void Slider::SetMinimum(int minValue) {
  this->lowerBound = minValue;
  this->curValue = (this->lowerBound+this->upperBound)/2;
  this->Repaint();
}

void Slider::SetMaximum(int maxValue) {
  this->upperBound = maxValue;
  this->curValue = (this->lowerBound+this->upperBound)/2;
  this->Repaint();
}

void Slider::SetCurValue(int value) {
  curValue = value;
  this->Repaint();
}

Slider::OrientationType Slider::GetOrientation() const {
  return orientation;
}

void Slider::SetOrientation(OrientationType orientation) {
  this->orientation = orientation;
  this->Repaint();
}

void Slider::PaintComponent(Graphics* g) {
  Rectangle realBound = this->GetBounds();

  //clear previous content if not done in component

  //draw the sliders

  //calculate the current thumb offset with cur value and min/max
  //derive the offset in number of pixels, get x position of the thumb from the left end
  double curRealX; //naming it curRealX here

  this->UpdateThumbLabel();
  g->FillRect(Rectangle(curRealX, realBound.y, 5.0, realBound.height));
  g->DrawText(Point(curRealX, realBound.y), this->thumbLabel);
}

string Slider::GetLowerBoundLabel() const {
  return lowerBoundLabel;
}

string Slider::GetUpperBoundLabel() const {
  return upperBoundLabel;
}

string Slider::GetThumbLabel() const {
  return thumbLabel;
}

void Slider::SetLowerBoundLabel(const string & label) {
  this->lowerBoundLabel = label;
  this->Repaint();
}

void Slider::SetUpperBoundLabel(const string & label) {
  this->upperBoundLabel = label;
  this->Repaint();
}

void Slider::SetThumbLabel(const string & label) {
  this->thumbLabel = label;
  this->Repaint();
}


bool Slider::HandleMouseEvent(const MouseEvent & e){
  if (!this->IsPointInComponent(e.GetWhere()) && !this->hasFocus()){
    if (e.GetType() == MouseEvent::mouseUp && this->hasFocus()){
      this->loseFocus();
    }
    return false;
  }
  switch(e.GetType()){
    case MouseEvent::mouseDown:
      this->focus();
      this->UpdateValue(e.GetWhere());
      this->NotifyListeners();
      return true;
      break;

    case MouseEvent::mouseUp:
      if (this->hasFocus()){
        this->loseFocus();
        return true;
      }
      break;

    case MouseEvent::mouseMove:
      if (this->hasFocus()){
        this->UpdateValue(e.GetWhere());
        this->NotifyListeners();
        return true;
      }
      break;

  }

  return false;
}

void Slider::UpdateValue(Point p){
  //3 cases of p.x
  //if left of min, set min
  //if right of max, set max
  //if in the middle, calculate the pixel percentages, and get the represented value
  //you might want to use double, or multiply by 100 before devision if you use int
  //if use double, +0.5 before rounding and casting to int
}

void Slider::UpdateThumbLabel(){
  stringstream ss;
  switch (this->GetSliderType()){
    case PLAYBACK:
      //make 20 intervals, half-hard-code the strings for each interval
      break;

    case CURRENTSTEP:
      //just showing the value in plain text
      ss << this->GetCurValue();
      this->thumbLabel = ss.str();
      break;

  }
}
void Slider::ValueChanged(Component* source){
  TurtleGraphics* turtle = (TurtleGraphics*)source;
  //update the values from turtle, 
  //maximum
  //current draw step
  //then update thumb and max label and repaint
}

Slider::SliderType Slider::GetSliderType() const{
  return this->type;
}
