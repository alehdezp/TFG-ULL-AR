# -----------------------
# Proyecto Final de Carrera
#  
#
# by Francisco de Sande <fsande@ull.es>
# date: 11.03.2011
# ---

LATEX    = latex
BIBTEX   = bibtex
PDFLATEX = pdflatex
L2HTML   = latex2html
DVIPS    = dvips
BASURA   = *.dvi *.bbl *.aux *.toc *.lof *.lot *.lol *.log *.blg *.out *~ *.pdf *.ps *.backup *.pdf.old

MASTER   = memoria-TFG-Alejandro.tex
SRC      =                                                          \
						Preface.tex 			                    \
						Cap1_Goals.tex                              \
						Cap2_Technology.tex                         \
						Cap3_ARInStudentEnvironments.tex	        \
						Cap4_TheApplication.tex						\
						Cap5_Conclussions.tex		\
						Cap6_Back-end.tex \

						

DVI = $(MASTER:.tex=.dvi)
BIB = $(MASTER:.tex=.bib)
PS  = $(MASTER:.tex=.ps)
PDF = $(MASTER:.tex=.pdf)
MASTERSIN =  $(MASTER:.tex=)

all: 
	touch memoria-TFG-Alejandro.`date +%Y-%m-%d`.pdf
	$(MAKE) pdf
	mv memoria-TFG-Alejandro.`date +%Y-%m-%d`.pdf memoria-TFG-Alejandro.`date +%Y-%m-%d`.pdf.old
	cp memoria-TFG-Alejandro.pdf memoria-TFG-Alejandro.`date +%Y-%m-%d`.pdf

HTML: $(PDF)
	$(L2HTML) -dir HTML -split 4 -local_icons -long_titles 20 $(MASTER)

pdf: $(MASTER) $(SRC)
	$(PDFLATEX) $(MASTER) && $(MAKE) bib && $(PDFLATEX) $(MASTER) && $(PDFLATEX) $(MASTER)

ps: $(MASTER) $(SRC) $(DVI)
	$(DVIPS) -o $(PS) $(DVI)

bib: bibliografia.bib
	$(BIBTEX) $(MASTERSIN)

$(DVI): $(MASTER) $(SRC)
	$(LATEX) $(MASTER); $(MAKE) bib; $(LATEX) $(MASTER); $(LATEX) $(MASTER)   

clean:
	$(RM) *.dvi *.bbl *.aux *.toc *.lof *.lot *.log *.blg *.lol *.brf *~ *.out HTML/*

del:
	$(RM) -R $(BASURA);
	cd bibliografia;    $(RM) -R $(BASURA);  \
	cd ../capitulos;    $(RM) -R $(BASURA);  \
	cd ../previo;       $(RM) -R $(BASURA);  \

mrproper:
	$(MAKE) delete

#.SUFFIXES: .tex .dvi. .ps .pdf
#
#%.dvi: %.tex
#	$(LATEX) $(SRC)
#
# end
# --
