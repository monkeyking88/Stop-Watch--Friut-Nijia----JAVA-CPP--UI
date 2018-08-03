
#include "Label.h"
#include "XWindow.h"

#include "Logging.h"

#include <algorithm>

using namespace std;

using namespace cs349;

Label::Label(const string & name,const string & label):Component(name)
{
    // Perform any initialization needed here
    this->label = label;
}

string Label::GetLabel() const {
    return label;
}

void Label::SetLabel(const string & label) {
    this->label = label;
}


void Label::PaintComponent(Graphics* g) {
    g->DrawRect(Rectangle(0,0,140,20));
    g->DrawText(Point(30,10),this->label);
    
}