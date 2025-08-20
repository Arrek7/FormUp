(function(){
  const exercisesContainer = document.getElementById('exercises-container');
  const addExerciseBtn = document.getElementById('add-exercise-btn');
  const saveTrainingBtn = document.getElementById('save-training-btn');
  const trainingNameInput = document.getElementById('training-name');
  const addExerciseNameBtn = document.getElementById('add-exercise-name-btn');
  const modal = document.getElementById('add-exercise-modal');
  const newExerciseInput = document.getElementById('new-exercise-name');
  const confirmAddExerciseBtn = document.getElementById('confirm-add-exercise');
  const cancelAddExerciseBtn = document.getElementById('cancel-add-exercise');
  const trainingForm = document.getElementById('training-form');
  const hiddenContainer = document.getElementById('training-hidden-inputs');

  // Lista ćwiczeń przekazana z backendu (Thymeleaf)
  const defaultOptions = [
    'Wyciskanie hantli',
    'Wyciskanie sztangi',
    'Przysiad ze sztangą',
    'Martwy ciąg',
    'Podciąganie nachwytem',
    'Wyciskanie żołnierskie',
    'Uginanie ramion ze sztangą',
    'Dipsy na poręczach'
  ];
  let exerciseOptions = Array.isArray(window.EXERCISE_NAMES) && window.EXERCISE_NAMES.length > 0
    ? window.EXERCISE_NAMES
    : defaultOptions;

  function openModal(){
    if (!modal) return;
    modal.classList.remove('hidden');
    modal.setAttribute('aria-hidden', 'false');
    newExerciseInput && (newExerciseInput.value = '');
    setTimeout(() => newExerciseInput && newExerciseInput.focus(), 0);
  }
  function closeModal(){
    if (!modal) return;
    modal.classList.add('hidden');
    modal.setAttribute('aria-hidden', 'true');
  }

  function ensureOptionExistsInSelect(selectEl, value){
    if (!selectEl) return;
    const exists = Array.from(selectEl.options).some(o => (o.value || o.textContent) === value);
    if (!exists){
      const opt = document.createElement('option');
      opt.value = value; opt.textContent = value;
      selectEl.appendChild(opt);
    }
  }

  function addExerciseNameToAllSelects(name){
    const trimmed = (name || '').trim();
    if (!trimmed) return false;
    // sprawdź duplikaty case-insensitive
    const existsCI = exerciseOptions.some(n => n.toLowerCase() === trimmed.toLowerCase());
    if (!existsCI){
      exerciseOptions.push(trimmed);
      // opcjonalnie sortuj alfabetycznie
      exerciseOptions.sort((a,b) => a.localeCompare(b, 'pl', { sensitivity: 'base' }));
    }
    // zaktualizuj wszystkie selecty
    const selects = document.querySelectorAll('select.exercise-select');
    selects.forEach(sel => ensureOptionExistsInSelect(sel, trimmed));
    // ustaw w ostatnim select
    const last = selects[selects.length - 1];
    if (last) last.value = trimmed;
    return true;
  }

  function createExerciseCard(defaultName){
    const card = document.createElement('section');
    card.className = 'exercise-card';

    const header = document.createElement('div');
    header.className = 'exercise-header';

    const title = document.createElement('h3');
    title.className = 'exercise-title';
    title.textContent = 'Ćwiczenie';

    const select = document.createElement('select');
    select.className = 'exercise-select';
    exerciseOptions.forEach(opt => {
      const o = document.createElement('option');
      o.value = opt; o.textContent = opt;
      select.appendChild(o);
    });
    if (defaultName && exerciseOptions.includes(defaultName)) {
      select.value = defaultName;
    }

    header.appendChild(title);
    header.appendChild(select);

    const list = document.createElement('ul');
    list.className = 'series-list';

    const addSetBtn = document.createElement('button');
    addSetBtn.type = 'button';
    addSetBtn.className = 'add-set-btn';
    addSetBtn.title = 'Dodaj serię';
    addSetBtn.textContent = '+';

    function addSeriesRow(idx){
      const li = document.createElement('li');
      li.className = 'series-row';

      const label = document.createElement('div');
      label.className = 'series-label';
      label.textContent = `Seria: ${idx}`;

      const weight = document.createElement('div');
      weight.className = 'input-wrap';
      const wInput = document.createElement('input');
      wInput.type = 'number';
      wInput.min = '0';
      wInput.step = '0.5';
      wInput.placeholder = 'Ciężar';
      wInput.value = '10';
      const wUnit = document.createElement('span');
      wUnit.className = 'unit';
      wUnit.textContent = 'kg';
      weight.appendChild(wInput); weight.appendChild(wUnit);

      const reps = document.createElement('div');
      reps.className = 'input-wrap';
      const rInput = document.createElement('input');
      rInput.type = 'number';
      rInput.min = '1';
      rInput.step = '1';
      rInput.placeholder = 'Powtórzenia';
      rInput.value = '8';
      const rUnit = document.createElement('span');
      rUnit.className = 'unit';
      rUnit.textContent = 'rep';
      reps.appendChild(rInput); reps.appendChild(rUnit);

      li.appendChild(label);
      li.appendChild(weight);
      li.appendChild(reps);
      list.appendChild(li);
    }

    // Domyślne 3 serie
    addSeriesRow(1);
    addSeriesRow(2);
    addSeriesRow(3);

    addSetBtn.addEventListener('click', () => {
      const next = list.children.length + 1;
      addSeriesRow(next);
    });

    card.appendChild(header);
    card.appendChild(list);
    card.appendChild(addSetBtn);

    return {
      element: card,
      getData(){
        const name = select.value;
        const sets = Array.from(list.children).map(li => {
          const inputs = li.querySelectorAll('input');
          return {
            weight: inputs[0].value === '' ? null : Number(inputs[0].value),
            reps: inputs[1].value === '' ? null : Number(inputs[1].value)
          };
        });
        return { name, sets };
      }
    };
  }

  const exerciseCards = [];

  function addExercise(defaultName){
    const card = createExerciseCard(defaultName);
    exercisesContainer.appendChild(card.element);
    exerciseCards.push(card);
  }

  // Inicjalizacja UI
  addExercise(exerciseOptions[0] || 'Wyciskanie hantli');

  addExerciseBtn?.addEventListener('click', () => addExercise());

  addExerciseNameBtn?.addEventListener('click', openModal);
  cancelAddExerciseBtn?.addEventListener('click', closeModal);
  modal?.addEventListener('click', (e) => {
    if (e.target === modal) closeModal();
  });
  document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') closeModal();
  });
  confirmAddExerciseBtn?.addEventListener('click', () => {
    const name = newExerciseInput?.value || '';
    if (!name.trim()) {
      newExerciseInput?.focus();
      return;
    }
    addExerciseNameToAllSelects(name);
    closeModal();
  });
  newExerciseInput?.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      confirmAddExerciseBtn?.click();
    }
  });

  // Serializacja formularza do ukrytych pól
  function rebuildHiddenInputs(){
    if (!hiddenContainer) return;
    hiddenContainer.innerHTML = '';
    exerciseCards.forEach((card, i) => {
      const data = card.getData();
      const nameInput = document.createElement('input');
      nameInput.type = 'hidden';
      nameInput.name = `exercises[${i}].name`;
      nameInput.value = data.name || '';
      hiddenContainer.appendChild(nameInput);

      data.sets.forEach((s, j) => {
        const w = document.createElement('input');
        w.type = 'hidden';
        w.name = `exercises[${i}].sets[${j}].weight`;
        w.value = s.weight == null ? '' : String(s.weight);
        hiddenContainer.appendChild(w);

        const r = document.createElement('input');
        r.type = 'hidden';
        r.name = `exercises[${i}].sets[${j}].repetition`;
        r.value = s.reps == null ? '' : String(s.reps);
        hiddenContainer.appendChild(r);
      });
    });
  }

  trainingForm?.addEventListener('submit', () => {
    rebuildHiddenInputs();
    // pozwól formularzowi się wysłać
  });

})();
