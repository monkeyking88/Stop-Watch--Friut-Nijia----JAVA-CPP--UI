//
//  ButtonPanel.h
//  CS349_A2
//
//  Created by Mengqi Liu on 2/3/2014.
//
//

#include "Panel.h"

using namespace std;
using namespace cs349;

namespace cs349 {
    /*
     this is the implemenation of a Playback Panel
     */
    class ButtonPanel: public Panel {
    public:
        /**
         * Constructs the PlayBackPanel for the application.
         
         * You should set the size of this panel in the constructor so
         * that information can be used to size the XWindow to the
         * appropriate size.
         */
        ButtonPanel(const string & text1,const string & text2,const string & text3, const string & text4,const string & text5);
    };
}
