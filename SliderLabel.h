#include "Component.h"
#include "Point.h"

using namespace std;

namespace cs349 {
    
    class Label : public Component {
        
    private:
        string label;
        Point p;
    protected:
        /**
         * The method that does the actual painting of the component once
         * the Graphics context object has been set up by the Paint
         * method in Component.
         */
        virtual void PaintComponent(Graphics* g);
        
    public:
        /**
         * Creates a new instance of a Label. For this class, name is a
         * unique name to uniquely identify this Label in unit tests and
         * within the interactor tree.
         *
         * @param name A unique name to identify this Label in debugging,
         * unit tests, and within the interactor tree
         * @param label The text shown in this label, if it is a
         * text-based label
         */
        SliderLabel(const string & label, Point pos);
        
        string GetLabel() const;
        
        /**
         * Sets the label context for this label
         */
        void SetLabel(const string & text);
                
    };
}

