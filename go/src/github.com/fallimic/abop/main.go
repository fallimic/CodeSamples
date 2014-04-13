
package main

import (
"os"
"github.com/fallimic/abop/lsystem"
"fmt"
"flag"
)

func main(){
	filename := flag.String("file", "rules.rule", "File to read rules from")
	flag.Parse()
	file, err := os.Open(*filename)
	if (err != nil){
		fmt.Printf("Bad File\n");
	}
	l := lsystem.GenerateLSystem(file,"F+F+F+F", 4)
	l.Start()
	l.Output("test.svg", "test.config")
	fmt.Printf("%v\n", l)
}
