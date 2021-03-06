package lsystem

import (
	"encoding/json"
	"os"
	"log"
	"fmt"
	"sync"
)

type LSystem struct {
	Productions map[rune][]rune;
	MaxGeneration int;
	output chan Element
	Initial []rune
}

type rule struct {
	Character string
	Result string
}

type rulefile struct {
	Initial string
	NumGen int
	Rules []rule
}

func GenerateLSystem(file *os.File, initial string, maxgeneration int) *LSystem {
	decode := json.NewDecoder(file)

	var rules rulefile
	err := decode.Decode(&rules)
	if (err != nil){
		log.Fatal("Error Decoding")
	}

	production := make(map[rune][]rune)
	for _, r := range rules.Rules {
		production[rune(r.Character[0])] = []rune(r.Result)
	}	
	count = Counter{
		done: make(chan int),}

	return &LSystem{
		MaxGeneration: rules.NumGen, 
		Productions: production,
		Initial: []rune(rules.Initial),}
}

type Element struct {
	character rune
	position int
	generation int
	system *LSystem
}

type Counter struct {
	sync.Mutex
	count int
	done chan int
}

var count Counter

func (element Element) String() string{
	return fmt.Sprintf("Character %s, Position %d\n", string(element.character), element.position)
}

func (element Element) run(){
	fmt.Printf(element.String())
	if (element.generation >= element.system.MaxGeneration){
		outputreader.Lock()
		outputreader.insert(element)
		outputreader.Unlock()
		count.Lock()
		count.count--
		if (count.count == 0){
			count.done <- 0
		}
		count.Unlock()
		return
	}
	result := element.system.Productions[element.character]
	for i, r := range result {
		child := Element{
			character: r,
			position: 100 * element.position + i, 
			system: element.system,
			generation: element.generation + 1,}
		count.Lock()
		count.count++
		count.Unlock()
		go child.run()
	}
	count.Lock()
	count.count--
	count.Unlock()
}

var outputreader *OutputReader

func (system *LSystem) Start() {
	outputreader = MakeOutputReader()
//	go outputreader.run()
	
	for i, r := range system.Initial {
		child := Element{
			character: r,
			position: i + 1,
			system: system,
			generation: 0,
		}
		count.count++
		go child.run()
	}
	<-count.done
	outputreader.MakeChars()
}

func (system *LSystem) Output(filename, configname string){

	turtle := MakeTurtleInterpreter(outputreader.outputchars, filename, configname)
	turtle.WriteFile()
}
