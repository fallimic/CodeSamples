package lsystem

import (
"fmt"
"sync"
)

type OutputReader struct {
	sync.Mutex
	output []Element
	outputchars []rune
	numElements int
}

func MakeOutputReader() *OutputReader {
	return &OutputReader{
		numElements: 0,
		outputchars: make([]rune, 100),
		output: make([]Element, 100),
	}
}



func (o *OutputReader) insert(el Element) {
/*	if (o.numElements == 0){
		o.output[0] = el
		o.numElements++
		return
	} else {
		if (o.numElements >= len(o.output)){
			temp := make([]Element, 2 * len(o.output))
			copy(temp, o.output)
			o.output = temp
		}

		for i := 0; i < o.numElements; i++ {
			el2 := o.output[i]
			if (el2.position > el.position){
				for j := i; j < len(o.output)-1; j++ {
					o.output[j+1] = o.output[j]
				}
				o.output[i] = el
			}
		}
		o.numElements++
	}
*/
	if (o.numElements >= len(o.output)){
			temp := make([]Element, 2 * len(o.output))
			copy(temp, o.output)
			o.output = temp
		}
	fmt.Printf("Num Elements: %d\n", o.numElements)
	o.output[o.numElements] = el
	o.numElements++
}


func (o *OutputReader) Print(){
	for _, el := range o.output {
		fmt.Printf("%r", el.character)
	}
}

func (o *OutputReader) MakeChars(){
	for i, el := range o.output {
		if (i >= len(o.outputchars)){
			temp := make([]rune, 2 * len(o.outputchars))
			copy(temp, o.outputchars)
			o.outputchars = temp
		}
		o.outputchars[i] = el.character
	}
}
