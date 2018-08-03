#include "Panel.h"


using namespace cs349;

namespace cs349 {
    /*
     this is the implemenation of a Playback Panel
     */
    class PlaybackPanel: public Panel {
    public:
        /**
         * Constructs the PlayBackPanel for the application. 
         
         * You should set the size of this panel in the constructor so
         * that information can be used to size the XWindow to the
         * appropriate size.
         */
        PlaybackPanel(const string & text);
    };
}
